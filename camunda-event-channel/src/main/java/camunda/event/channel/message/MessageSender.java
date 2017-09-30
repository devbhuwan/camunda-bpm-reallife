package camunda.event.channel.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageSender {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MessageChannel input;

    public void send(Message<?> m) {
        try {
            String jsonMessage = mapper.writeValueAsString(m);
            input.send(MessageBuilder.withPayload(jsonMessage).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

}
