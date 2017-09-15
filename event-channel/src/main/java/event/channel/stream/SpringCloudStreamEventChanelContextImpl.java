package event.channel.stream;

import event.channel.contracts.EventChanelContext;
import event.channel.contracts.EventListenerSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConditionalOnExpression()
@Component
@Slf4j
public class SpringCloudStreamEventChanelContextImpl implements EventChanelContext {

    @Override
    public void registerListener(EventListenerSpec eventListenerSpec) {
        log.info("Registering Listener [{}]", eventListenerSpec.toString());
    }

}
