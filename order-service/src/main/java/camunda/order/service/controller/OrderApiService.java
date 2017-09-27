package camunda.order.service.controller;

import camunda.event.bus.connector.contracts.CamundaMessageStartCmd;
import camunda.event.bus.connector.contracts.ImmutableCamundaMessageStartCmd;
import camunda.order.domain.Order;
import camunda.order.service.bpmn.metadata.OrderProcessConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/order")
public class OrderApiService {

    @Autowired
    private Sink sink;

    @PostMapping("/create")
    public String create(Order order) {
        MessageBuilder<CamundaMessageStartCmd> payload = MessageBuilder
                .withPayload(ImmutableCamundaMessageStartCmd.builder()
                        .messageKey(OrderProcessConstants.Ids.CREATE_ORDER_MSG)
                        .variables(new HashMap<>())
                        .build());
        sink.input().send(payload.build());
        return "Successfully created your an order";
    }

}
