package camunda.order.service.delegates;

import camunda.order.domain.Order;
import camunda.order.service.persistance.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitiateOrder implements JavaDelegate {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Order order = (Order) execution.getVariable("order");
        order.setProcessInstanceId(execution.getProcessInstanceId());
        orderRepository.placeOrder(order);
    }

}
