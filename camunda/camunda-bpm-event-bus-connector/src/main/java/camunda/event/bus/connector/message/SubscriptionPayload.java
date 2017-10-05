package camunda.event.bus.connector.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SubscriptionPayload {

    private Map<String, Object> processVariables;

    public SubscriptionPayload(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

}
