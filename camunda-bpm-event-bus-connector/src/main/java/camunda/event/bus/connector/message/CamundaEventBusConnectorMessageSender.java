package camunda.event.bus.connector.message;

import camunda.event.channel.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class CamundaEventBusConnectorMessageSender {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Sink sink;

    public void send(Message<?> message) {
        try {
            String jsonMessage = mapper.writeValueAsString(message);
            sink.input().send(MessageBuilder.withPayload(jsonMessage).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

}
