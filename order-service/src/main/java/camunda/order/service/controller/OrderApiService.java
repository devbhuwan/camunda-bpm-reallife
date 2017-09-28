package camunda.order.service.controller;

import camunda.event.bus.connector.endpoint.message.CamundaEventBusConnectorMessageSender;
import camunda.event.bus.connector.endpoint.message.StartProcessByMessageCommandPayload;
import camunda.event.channel.message.Message;
import camunda.order.domain.Order;
import camunda.order.service.persistance.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static camunda.event.bus.connector.endpoint.message.CamundaEventBusConnectorMessageListener.START_PROCESS_BY_MESSAGE_COMMAND;
import static camunda.order.service.bpmn.metadata.OrderProcessConstants.Ids.CREATE_ORDER_MSG;

@RestController
@RequestMapping("/order")
public class OrderApiService {

    @Autowired
    private CamundaEventBusConnectorMessageSender camundaEventBusConnectorMessageSender;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public String create(Order order) {
        camundaEventBusConnectorMessageSender.send(new Message<>(START_PROCESS_BY_MESSAGE_COMMAND,
                "order-service", new StartProcessByMessageCommandPayload(CREATE_ORDER_MSG)));
        return "Successfully created your an order";
    }

    @GetMapping("/all")
    public Collection<Order> orders() {
        return orderRepository.findAll();
    }

}
