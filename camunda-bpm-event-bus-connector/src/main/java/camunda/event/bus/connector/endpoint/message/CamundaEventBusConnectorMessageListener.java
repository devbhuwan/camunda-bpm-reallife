package camunda.event.bus.connector.endpoint.message;

import camunda.event.channel.contracts.EventHandler;
import camunda.event.channel.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableBinding(Sink.class)
public class CamundaEventBusConnectorMessageListener {

    public static final String START_MESSAGE_EVENT = "START_MESSAGE_EVENT";
    public static final String START_PROCESS_BY_MESSAGE_COMMAND = "StartProcessByMessageCommand";
    private final Map<String, Set<String>> endpointMessageEventNames = new ConcurrentHashMap<>();
    @Autowired
    @Lazy
    private RuntimeService runtimeService;

    public CamundaEventBusConnectorMessageListener() {
        endpointMessageEventNames.put(START_MESSAGE_EVENT, new HashSet<>());
    }

    @EventHandler("payload.messageType.toString()=='" + START_PROCESS_BY_MESSAGE_COMMAND + "'")
    @Transactional
    public void startProcessByMessageCommandReceived(String messageJson) throws Exception {
        Message<StartProcessByMessageCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<StartProcessByMessageCommandPayload>>() {
        });
        StartProcessByMessageCommandPayload commandPayload = message.getPayload();
        if (isMessageBelongsToCurrentEndpoint(commandPayload))
            runtimeService.createMessageCorrelation(commandPayload.getMessageName())
                    //.processInstanceBusinessKey(message.getTraceId())
                    .setVariables(commandPayload.getVariables())
                    .correlateWithResult();
    }

    private boolean isMessageBelongsToCurrentEndpoint(StartProcessByMessageCommandPayload commandPayload) {
        return endpointMessageEventNames.get(START_MESSAGE_EVENT).contains(commandPayload.getMessageName());
    }

    public void registerEndpointMessageEventName(String key, String messageName) {
        this.endpointMessageEventNames.get(key).add(messageName);
    }

}
