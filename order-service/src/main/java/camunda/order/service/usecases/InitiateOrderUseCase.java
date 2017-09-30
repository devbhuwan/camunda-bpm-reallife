package camunda.order.service.usecases;

import camunda.order.domain.Order;
import camunda.order.service.persistance.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitiateOrderUseCase implements JavaDelegate {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        Order order = (Order) execution.getVariable("order");
        order.setProcessInstanceId(execution.getProcessInstanceId());
        orderRepository.placeOrder(order);
    }

}
