package camunda.event.bus.connector.endpoint.message;


import camunda.CamundaBpmEventBusConnectorApplication;
import camunda.event.channel.message.Message;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.camunda.bpm.scenario.run.ProcessRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static camunda.bpmn.metadata.EventBusConnectorOrderProcessConstants.Ids.END_ORDER_EVENT;
import static camunda.bpmn.metadata.EventBusConnectorOrderProcessConstants.Ids.ORDER_ENTRY;
import static camunda.event.bus.connector.endpoint.message.CamundaEventBusConnectorMessageListener.START_PROCESS_BY_MESSAGE_COMMAND;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamundaBpmEventBusConnectorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "camunda.bpm.auto-deployment-enabled=false"
        }
)
@Deployment(resources = "EVENT_BUS_CONNECTOR_ORDER_PROCESS.bpmn")
public class StartProcessByMessageCommandTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private CamundaEventBusConnectorMessageSender camundaEventBusConnectorMessageSender;
    @Mock
    private ProcessScenario orderProcess;
    @Mock
    private ProcessScenario paymentProcess;
    @Autowired
    private RuntimeService runtimeService;

    @PostConstruct
    void initRule() {
        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenMessageTypeStartProcessByMessageCommand_whenPublish_thenStartProcessesByMessageName() throws Exception {
        ProcessRunner.ExecutableRunner.StartingByStarter starter = Scenario.run(orderProcess)
                .startBy(() -> {
                    camundaEventBusConnectorMessageSender.send(new Message<>(START_PROCESS_BY_MESSAGE_COMMAND,
                            new StartProcessByMessageCommandPayload("CREATE_ORDER_MSG")));
                    return runtimeService.createProcessInstanceQuery().active().singleResult();
                });
        when(orderProcess.waitsAtUserTask(ORDER_ENTRY))
                .thenReturn(TaskDelegate::complete);
        starter.execute();
        Mockito.verify(orderProcess).hasFinished(END_ORDER_EVENT);
    }


}