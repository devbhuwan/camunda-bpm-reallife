package camunda.event.channel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConfigurationProperties(prefix = EventChannelProperties.CONFIG_PROPERTIES_PREFIX)
@Validated
@Getter
@Setter
public class EventChannelProperties {

    static final String CONFIG_PROPERTIES_PREFIX = "event.channel";

    @NotEmpty
    private String type;

    private String host;
    private String port;

}
