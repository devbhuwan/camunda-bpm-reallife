package event.channel.stream;

import event.channel.TestApplication;
import event.channel.contracts.EventChanelContext;
import event.channel.contracts.ImmutableEventListenerSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@ComponentScan(basePackageClasses = TestApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringCloudStreamEventChanelContextImplTest {

    public static final String START_EVENT_MESSAGE = "START_EVENT_MESSAGE";

    @Autowired
    private EventChanelContext eventChanelContext;

    @Test
    public void givenListener_whenRegister_thenConnectEventListenerWithEventBus() {
        eventChanelContext.registerListener(ImmutableEventListenerSpec.builder()
                .message(START_EVENT_MESSAGE)
                .build());
    }
}