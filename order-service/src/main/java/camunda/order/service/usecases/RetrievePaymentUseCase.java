package camunda.order.service.usecases;


import camunda.bpm.api.integrator.util.VariablesUtil;
import camunda.event.channel.message.Message;
import camunda.event.channel.message.MessageSender;
import camunda.order.domain.Order;
import camunda.order.service.bpmn.metadata.OrderProcessConstants;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetrievePaymentUseCase implements JavaDelegate {


    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        Order order = VariablesUtil.toTypeObject(execution.getVariable("order"), Order.class);
        messageSender.send(new Message<>(OrderProcessConstants.Ids.RetrievePaymentCommand,
                new RetrievePaymentCommandPayload(order.getId(), order.getAmount())));
    }

}
