package camunda.event.bus.connector.endpoint.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class StartProcessByMessageCommandPayload {

    private String messageName;
    private Map<String, Object> variables;

    public StartProcessByMessageCommandPayload(String messageName) {
        this.messageName = messageName;
    }

    public StartProcessByMessageCommandPayload(String messageName, Map<String, Object> variables) {
        this.messageName = messageName;
        this.variables = variables;
    }


}
