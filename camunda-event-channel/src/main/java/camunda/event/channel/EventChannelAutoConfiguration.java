package camunda.event.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.config.BinderProperties;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
            inputProperties.setDestination("dataIn");
            inputProperties.setBinder("rabbit");
            bindingProperties.put("input", inputProperties);

            final BindingProperties outputProperties = new BindingProperties();
            outputProperties.setDestination("dataOut");
            outputProperties.setBinder("rabbit");
            bindingProperties.put("output", outputProperties);

            bindingServiceProperties.setBindings(bindingProperties);

            Map<String, BinderProperties> binders = new HashMap<>();
            BinderProperties binderProperties = new BinderProperties();
            binderProperties.setType("rabbit");
            Properties props = new Properties();
            props.put("spring.rabbitmq.host", properties.getHost());
            props.put("spring.rabbitmq.port", properties.getPort());

            binderProperties.setEnvironment(props);
            binders.put("rabbit", binderProperties);

            bindingServiceProperties.setBinders(binders);
        } else if (ChannelType.KAFKA.name().equals(properties.getType())) {

        } else {
            throw new UnsupportedOperationException("Event channel does not support for [" + properties.getType() + "]");
        }
    }
}
