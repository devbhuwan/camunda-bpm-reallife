package camunda.bpmn.scenario;

import camunda.bpmn.bpmn.metadata.ApprovalProcessConstants;
import camunda.bpmn.bpmn.metadata.ProcessCallActivityConstants;
import order.service.OrderServiceApplication;
import order.service.bpmn.metadata.OrderProcessConstants;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.camunda.bpm.scenario.run.ProcessRunner.ExecutableRunner.StartingByStarter;
import org.camunda.bpm.scenario.run.ProcessStarter;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = OrderServiceApplication.class
        , properties = {"camunda.bpm.auto-deployment-enabled=false"}
)
@Deployment(resources = {"Approval_Process.bpmn", "Process_Call_Activity.bpmn"})
public class ProcessCallActivityTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule;
    @Mock
    private ProcessScenario processScenario;
    @Mock
    private ProcessScenario activityProcessScenario;
    @Autowired
    private ProcessEngine processEngine;

    @PostConstruct
    void initRule() {
        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void orderProcess() {
        StartingByStarter starter = Scenario.run(processScenario)
                .startBy(start());
        when(processScenario.runsCallActivity(ProcessCallActivityConstants.Ids.CALL_ACTIVITY))
                .thenReturn(Scenario.use(activityProcessScenario));
        when(activityProcessScenario.waitsAtUserTask(ApprovalProcessConstants.Ids.AP_USER_TASK)).thenReturn(TaskDelegate::complete);
        starter.execute();
        verify(processScenario).hasFinished(OrderProcessConstants.Ids.END_ORDER_EVENT);
    }

    private ProcessStarter start() {
        return () -> processEngine.getRuntimeService().startProcessInstanceByKey(ProcessCallActivityConstants.Ids.Process_Call_Activity);
    }
}
