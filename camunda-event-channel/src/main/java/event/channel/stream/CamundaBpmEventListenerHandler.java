package event.channel.stream;

import event.channel.contracts.CamundaMessageStartEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class CamundaBpmEventListenerHandler {

    @StreamListener(Sink.INPUT)
    public void bpmStartEventListener(CamundaMessageStartEvent messageStartEvent) {
        log.info("Hello" + " " + messageStartEvent);
    }

}
