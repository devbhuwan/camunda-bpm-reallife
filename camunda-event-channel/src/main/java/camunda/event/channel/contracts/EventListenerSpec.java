package camunda.event.channel.contracts;

import org.immutables.value.Value;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Value.Immutable
public interface EventListenerSpec {

    String message();

    Object handleEventBean();

}
