package camunda.event.channel.stream;

import camunda.event.channel.contracts.EventChanelContext;
import camunda.event.channel.contracts.EventListenerSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;

    @Override
    public void registerListener(EventListenerSpec eventListenerSpec) {
        log.info("Registering Listener [{}]", eventListenerSpec.toString());
        configurableBeanFactory.registerSingleton(eventListenerSpec.message(), eventListenerSpec.handleEventBean());
        streamListenerAnnotationBeanPostProcessor.postProcessAfterInitialization(eventListenerSpec.handleEventBean(), eventListenerSpec.message());
    }

}
