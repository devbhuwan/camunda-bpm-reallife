package event.channel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConfigurationProperties(prefix = EventChannelProperties.CONFIG_PROPERTIES_PREFIX)
@Validated
public class EventChannelProperties {

    static final String CONFIG_PROPERTIES_PREFIX = "event.channel";

    private String type;
}
