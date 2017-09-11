package order.service;

import order.service.bpmn.metadata.OrderProcessConstants;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.camunda.bpm.scenario.run.ProcessRunner.ExecutableRunner.StartingByStarter;
import org.camunda.bpm.scenario.run.ProcessStarter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Deployment
public class OrderProcessTest {

    @Mock
    private ProcessScenario orderProcess;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProcessEngine processEngine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void orderProcess() {
        StartingByStarter starter = Scenario.run(orderProcess)
                .startBy(start());
        when(orderProcess.waitsAtUserTask(OrderProcessConstants.Ids.ORDER_ENTRY)).thenReturn(TaskDelegate::complete);
        starter.execute();
        Mockito.verify(orderProcess).hasFinished(OrderProcessConstants.Ids.END_ORDER_EVENT);
    }

    private ProcessStarter start() {
        return () -> processEngine.getRuntimeService().startProcessInstanceByMessage(OrderProcessConstants.Ids.START_ORDER_EVENT);
    }

}