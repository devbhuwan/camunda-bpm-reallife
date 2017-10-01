package camunda.order.service.usecases;


import camunda.bpm.decorator.util.VariablesUtil;
import camunda.event.bus.connector.message.SubscriptionPayload;
import camunda.event.channel.message.Message;
import camunda.event.channel.message.MessageSender;
import camunda.order.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static camunda.order.service.bpmn.metadata.OrderProcessConstants.Ids.RetrievePaymentCommand;

@Component
@Slf4j
public class RetrievePaymentUseCase implements JavaDelegate {

    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        Order order = VariablesUtil.toTypeObject(execution.getVariable("order"), Order.class);
        HashMap<String, Object> processVariables = new HashMap<>();
        processVariables.put(RetrievePaymentCommand, new RetrievePaymentCommandPayload(order.getId(), order.getAmount()));
        messageSender.send(new Message<>(RetrievePaymentCommand, new SubscriptionPayload(processVariables)));
    }

}
