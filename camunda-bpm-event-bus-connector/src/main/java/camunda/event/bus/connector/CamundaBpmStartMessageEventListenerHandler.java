package camunda.event.bus.connector;

import camunda.event.bus.connector.contracts.CamundaMessageStartEvent;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Lazy;

@Slf4j
@EnableBinding(Sink.class)
public class CamundaBpmStartMessageEventListenerHandler {

    @Autowired
    @Lazy
    private RepositoryService repositoryService;
    @Autowired
    @Lazy
    private RuntimeService runtimeService;
    @Value("${camunda.bpm.decorator.businessKey:${spring.application.name}}")
    private String businessKey;

    @StreamListener(Sink.INPUT)
    public void bpmStartEventListener(CamundaMessageStartEvent messageStartEvent) {
        log.info("Start Bpm Message Event [{}]", messageStartEvent.toString());
        runtimeService.createMessageCorrelation(messageStartEvent.messageKey())
                .correlateStartMessage();
    }

}
