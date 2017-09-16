package camunda.event.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@EnableConfigurationProperties(EventChannelProperties.class)
public class EventChannelAutoConfiguration {

    @Autowired
    private EventChannelProperties properties;
}
