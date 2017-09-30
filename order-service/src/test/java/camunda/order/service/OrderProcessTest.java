package camunda.order.service;

import camunda.order.domain.Order;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
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

import static camunda.order.service.bpmn.metadata.OrderProcessConstants.Ids.*;
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
@Deployment(resources = {"ORDER_PROCESS.bpmn"})
public class OrderProcessTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule;
    @Mock
    private ProcessScenario orderProcess;
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
        StartingByStarter starter = Scenario.run(orderProcess)
                .startBy(start());
        when(orderProcess.waitsAtUserTask(ORDER_ENTRY))
                .thenReturn(taskDelegate ->
                        taskDelegate.complete(Variables.putValue("order", new Order("Sandwich", 10))));

        starter.execute();
        verify(orderProcess).hasFinished(END_ORDER_EVENT);
    }

    private ProcessStarter start() {
        return () -> processEngine.getRuntimeService().startProcessInstanceByKey(ORDER_PROCESS);
    }


}