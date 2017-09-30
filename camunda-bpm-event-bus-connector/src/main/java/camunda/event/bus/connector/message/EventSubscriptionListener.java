package camunda.event.bus.connector.message;

import camunda.event.channel.contracts.EventHandler;
import camunda.event.channel.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableBinding(Sink.class)
@Slf4j
public class EventSubscriptionListener {

    private final Map<String, Object> endpointMessageNames = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Lazy
    private RuntimeService runtimeService;

    public void register(String messageName) {
        if (endpointMessageNames.containsKey(messageName))
            return;
        endpointMessageNames.put(messageName, "");
    }

    @EventHandler()
    public void eventSubscriptionListener(String messageJson) {
        try {
            Message<SubscriptionPayload> payloadMessage = objectMapper.readValue(messageJson,
                    new TypeReference<Message<SubscriptionPayload>>() {
                    });
            if (payloadMessage != null) {
                executeMessage(payloadMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeMessage(Message<SubscriptionPayload> payload) {
        if (endpointMessageNames.containsKey(payload.getMessageType())) {
            if (StringUtils.isNotEmpty(payload.getTraceId()) && payload.getPayload().getProcessVariables() != null)
                runtimeService.correlateMessage(payload.getMessageType(), payload.getTraceId(), payload.getPayload().getProcessVariables());
            else if (StringUtils.isNotEmpty(payload.getTraceId()) && payload.getPayload().getProcessVariables() == null)
                runtimeService.correlateMessage(payload.getMessageType(), payload.getTraceId());
            else if (payload.getPayload().getProcessVariables() != null)
                runtimeService.correlateMessage(payload.getMessageType(), new HashMap<>(), payload.getPayload().getProcessVariables());
            else
                runtimeService.correlateMessage(payload.getMessageType());
        }
    }

}
