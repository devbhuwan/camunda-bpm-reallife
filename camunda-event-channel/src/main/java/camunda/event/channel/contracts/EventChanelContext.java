package camunda.event.channel.contracts;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public interface EventChanelContext {

    void registerListener(EventListenerSpec eventListenerSpec);
}
