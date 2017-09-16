package camunda.event.bus.connector.contracts;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface CamundaMessageStartEvent {

    String processDefinitionId();

    String messageKey();

    Map<String, Object> variables();

}
