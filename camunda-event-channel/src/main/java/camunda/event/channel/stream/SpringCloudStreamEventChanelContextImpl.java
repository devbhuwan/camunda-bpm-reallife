package camunda.event.channel.stream;

import camunda.event.channel.contracts.EventChanelContext;
import camunda.event.channel.contracts.EventListenerSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.StreamListenerAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Component
@Slf4j
public class SpringCloudStreamEventChanelContextImpl implements EventChanelContext {

    @Autowired
    private StreamListenerAnnotationBeanPostProcessor streamListenerAnnotationBeanPostProcessor;

    @Override
    public void registerListener(EventListenerSpec eventListenerSpec) {
        log.info("Registering Listener [{}]", eventListenerSpec.toString());
        streamListenerAnnotationBeanPostProcessor.postProcessAfterInitialization(eventListenerSpec.handleEventBean(), eventListenerSpec.message());
    }

}
