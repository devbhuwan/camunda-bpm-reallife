package event.channel.stream;

import event.channel.TestApplication;
import event.channel.contracts.EventChanelContext;
import event.channel.contracts.ImmutableEventListenerSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringCloudStreamEventChanelContextImplTest {

    static final String START_EVENT_MESSAGE = "START_EVENT_MESSAGE";

    @Autowired
    private EventChanelContext eventChanelContext;
    @Autowired
    private Sink sink;
    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void givenListener_whenRegister_thenConnectEventListenerWithEventBus() {
        eventChanelContext.registerListener(ImmutableEventListenerSpec.builder()
                .message(START_EVENT_MESSAGE)
                .build());
        this.sink.input().send(MessageBuilder.withPayload());
    }
}