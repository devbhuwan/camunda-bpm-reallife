package camunda;


import camunda.event.bus.connector.message.SubscriptionPayload;
import camunda.event.channel.message.Message;
import camunda.event.channel.message.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

import static camunda.bpmn.metadata.EventBusConnectorOrderProcessConstants.Ids.RetrievePaymentCommand;

@Component
@Slf4j
public class RetrievePaymentUseCase implements JavaDelegate {

    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        HashMap<String, Object> processVariables = new HashMap<>();
        processVariables.put(RetrievePaymentCommand, new RetrievePaymentCommandPayload(1000L, BigDecimal.valueOf(415445)));
        messageSender.send(new Message<>(RetrievePaymentCommand, new SubscriptionPayload(processVariables)));
    }

}
