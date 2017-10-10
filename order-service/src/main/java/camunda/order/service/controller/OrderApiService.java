package camunda.order.service.controller;

import camunda.order.domain.Order;
import camunda.order.service.OrderProcessConstants;
import camunda.order.service.persistance.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/order")
public class OrderApiService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/create")
    public String create() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage(OrderProcessConstants.Ids.CreateOrderEvent);
        return processInstance.getId();
    }

    @GetMapping("/all")
    public Collection<Order> orders() {
        return orderRepository.findAll();
    }

}
