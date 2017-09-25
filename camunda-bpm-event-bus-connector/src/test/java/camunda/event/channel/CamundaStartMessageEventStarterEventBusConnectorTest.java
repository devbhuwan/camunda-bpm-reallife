package camunda.event.channel;

import camunda.TestApplication;
import camunda.event.bus.connector.contracts.CamundaMessageStartEvent;
import camunda.event.bus.connector.contracts.ImmutableCamundaMessageStartEvent;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "camunda.bpm.auto-deployment-enabled=false"
        }
)
public class CamundaStartMessageEventStarterEventBusConnectorTest {

    static final String CREATE_ORDER_MSG = "CREATE_ORDER_MSG";
    @Autowired
    private Sink sink;
    @Autowired
    private MessageCollector messageCollector;

    @Deployment(resources = "ORDER_PROCESS.bpmn")
    @Test
    public void givenListener_whenRegister_thenConnectEventListenerWithEventBus() {
        MessageBuilder<CamundaMessageStartEvent> startEventMessageBuilder = MessageBuilder.withPayload(ImmutableCamundaMessageStartEvent
                .builder()
                .messageKey(CREATE_ORDER_MSG)
                .variables(Variables.createVariables())
                .processDefinitionId("ORDER_PROCESS")
                .build());
        this.sink.input().send(startEventMessageBuilder.build());
    }
}