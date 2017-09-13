package camunda.event.bus.connector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConfigurationProperties(prefix = CamundaBpmEventBusConnectorProperties.PREFIX)
@Validated
@Getter
@Setter
public class CamundaBpmEventBusConnectorProperties {

    public static final String PREFIX = "camunda.bpm.event.bus.connector";
    private boolean enable;
}
