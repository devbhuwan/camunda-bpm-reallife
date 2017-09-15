package event.channel.contracts;

import org.immutables.value.Value;

@Value.Immutable
public interface CamundaMessageStartEvent {

    String processDefinitionId();

    String messageKey();
}
