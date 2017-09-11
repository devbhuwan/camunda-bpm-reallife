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
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Deployment
public class OrderProcessTest {

    @Mock
    private ProcessScenario orderProcess;
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Before
    public void init() {
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
        return () -> processEngine.getRuntimeService().startProcessInstanceByKey(OrderProcessConstants.Ids.ORDER_PROCESS);
    }

    @Test
    public void testSendReceive() {
        Message<String> message = new GenericMessage<>("hello");
        processor.input().send(message);
        Message<String> received = (Message<String>) messageCollector.forChannel(processor.output()).poll();
        assertThat(received.getPayload(), equalTo("hello world"));
    }


}