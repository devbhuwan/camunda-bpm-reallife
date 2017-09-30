package camunda;

import camunda.event.bus.connector.message.SubscriptionPayload;
import camunda.event.channel.message.Message;
import camunda.event.channel.message.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static camunda.bpmn.metadata.EventBusConnectorOrderProcessConstants.Ids.PaymentReceivedEvent;

@Component
@Slf4j
public class PaymentReceivedUseCase implements JavaDelegate {

    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        messageSender.send(new Message<>(PaymentReceivedEvent, new SubscriptionPayload()));
    }

}
