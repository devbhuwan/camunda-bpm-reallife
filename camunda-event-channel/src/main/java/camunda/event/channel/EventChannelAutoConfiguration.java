package camunda.event.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
        log.info("AutoConfiguration [{}]", EventChannelAutoConfiguration.class.getSimpleName());
    }
}
