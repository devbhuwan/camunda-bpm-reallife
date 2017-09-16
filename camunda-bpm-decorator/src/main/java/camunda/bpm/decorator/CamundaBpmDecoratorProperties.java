package camunda.bpm.decorator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@ConfigurationProperties(prefix = CamundaBpmDecoratorProperties.CAMUNDA_BPM_DECORATOR_PREFIX)
@Validated
@Getter
@Setter
public class CamundaBpmDecoratorProperties {

    public static final String CAMUNDA_BPM_DECORATOR_PREFIX = "camunda.bpm.decorator";
    private boolean enable = Boolean.TRUE;
    private String businessKey;
}
