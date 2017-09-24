package camunda.event.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@AutoConfigureBefore(BindingServiceConfiguration.class)
@EnableConfigurationProperties({BindingServiceProperties.class, EventChannelProperties.class})
@Slf4j
public class EventChannelAutoConfiguration {

    @Autowired
    private EventChannelProperties properties;
    @Autowired
    private BindingServiceProperties bindingServiceProperties;

    @PostConstruct
    private void configure() {
        if (ChannelType.RABBIT.name().equals(properties.getType())) {
            log.info("Configure RABBITMQ configuration........");
            bindingServiceProperties.setDefaultBinder("rabbit");
            HashMap<String, BindingProperties> bindingProperties = new HashMap<>();
            final BindingProperties inputProperties = new BindingProperties();
            inputProperties.setDestination("foo");
            inputProperties.setBinder("rabbit");
            final BindingProperties outputProperties = new BindingProperties();
            outputProperties.setDestination("bar");
            outputProperties.setBinder("rabbit");
            bindingProperties.put("output", outputProperties);
            bindingServiceProperties.setBindings(bindingProperties);
        } else if (ChannelType.KAFKA.name().equals(properties.getType())) {
        } else {
            throw new UnsupportedOperationException("Event channel does not support for [" + properties.getType() + "]");
        }
    }
}
