package camunda.order.service.usecases;

import camunda.bpm.decorator.util.VariablesUtil;
import camunda.order.domain.Order;
import camunda.order.service.persistance.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitiateOrderUseCase implements JavaDelegate {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delegating [{}]", this.getClass().getSimpleName());
        Order order = VariablesUtil.toTypeObject(execution.getVariable("order"), Order.class);
        order.setProcessInstanceId(execution.getProcessInstanceId());
        orderRepository.placeOrder(order);
    }

}
