package event.channel.stream;

import event.channel.contracts.EventChanelContext;
import event.channel.contracts.EventListenerSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.stream.binding.StreamListenerAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConditionalOnExpression()
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
        CamundaBpmEventListenerHandler camundaBpmEventListenerHandler = new CamundaBpmEventListenerHandler();
        configurableBeanFactory.registerSingleton(eventListenerSpec.message(), camundaBpmEventListenerHandler);
        streamListenerAnnotationBeanPostProcessor.postProcessAfterInitialization(camundaBpmEventListenerHandler, eventListenerSpec.message());
    }

}
