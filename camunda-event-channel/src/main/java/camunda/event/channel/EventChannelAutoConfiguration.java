package camunda.event.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@EnableConfigurationProperties(EventChannelProperties.class)
public class EventChannelAutoConfiguration {

    @Autowired
    private EventChannelProperties properties;

    @PostConstruct
    private void configure() {
        if (ChannelType.RABBIT.name().equals(properties.getType())) {
            //System.setProperty("spring.", properties.getHost());
        } else if (ChannelType.KAFKA.name().equals(properties.getType())) {
            //System.setProperty("", properties.getHost());
        } else {
            throw new UnsupportedOperationException("Event channel does not support for [" + properties.getType() + "]");
        }
    }
}
