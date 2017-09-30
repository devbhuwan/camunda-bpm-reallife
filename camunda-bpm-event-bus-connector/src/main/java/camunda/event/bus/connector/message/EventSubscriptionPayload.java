package camunda.event.bus.connector.message;

import java.util.HashMap;
import java.util.Map;

public class EventSubscriptionPayload {
    public Map<String, Object> getProcessVariables() {
        return new HashMap<>();
    }
}
