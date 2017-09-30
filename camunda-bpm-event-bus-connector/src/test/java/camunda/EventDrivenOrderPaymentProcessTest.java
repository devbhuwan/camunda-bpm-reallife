package camunda;

import camunda.event.bus.connector.message.SubscriptionPayload;
import camunda.event.channel.message.Message;
import camunda.event.channel.message.MessageSender;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.camunda.bpm.scenario.run.ProcessRunner.ExecutableRunner.StartingByStarter;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static camunda.bpmn.metadata.EventBusConnectorOrderProcessConstants.Ids.*;
import static camunda.bpmn.metadata.EventBusConnectorPaymentProcessConstants.Ids.PAYMENT_PROCESS;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamundaBpmEventBusConnectorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "camunda.bpm.auto-deployment-enabled=false"
        }
)
@Deployment(resources = {"EVENT_BUS_CONNECTOR_ORDER_PROCESS.bpmn", "EVENT_BUS_CONNECTOR_PAYMENT_PROCESS.bpmn"})
public class EventDrivenOrderPaymentProcessTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProcessEngine processEngine;
    @Mock
    private ProcessScenario orderProcess;
    @Mock
    private ProcessScenario paymentProcess;
    @Autowired
    private MessageSender messageSender;

    @PostConstruct
    void initRule() {
        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void orderPaymentProcess() throws InterruptedException {
        messageSender.send(new Message<>(CreateOrderEvent, new SubscriptionPayload()));
        StartingByStarter orderStarter = Scenario.run(orderProcess)
                .startBy(() -> processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processDefinitionKey(ORDER_PROCESS)
                        .active().singleResult());
        when(orderProcess.waitsAtUserTask(ORDER_ENTRY))
                .thenReturn(TaskDelegate::complete);
        StartingByStarter paymentStarter = Scenario.run(paymentProcess)
                .startBy(() -> processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processDefinitionKey(PAYMENT_PROCESS)
                        .active().singleResult());
        orderStarter.execute();
        paymentStarter.execute();
    }
}
