package event.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import javax.annotation.processing.Processor;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@EnableConfigurationProperties(EventChannelProperties.class)
public class EventChannelAutoConfiguration {

    @Autowired
    private EventChannelProperties properties;
}
