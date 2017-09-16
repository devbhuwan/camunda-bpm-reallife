package order.service.controller;

import camunda.event.bus.connector.contracts.CamundaMessageStartEvent;
import camunda.event.bus.connector.contracts.ImmutableCamundaMessageStartEvent;
import order.service.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderApiService {

    @Autowired
    private Sink sink;

    @PostMapping
    public String create(Order order) {
        MessageBuilder<CamundaMessageStartEvent> payload = MessageBuilder.withPayload(ImmutableCamundaMessageStartEvent
                .builder()
                .build());
        sink.input().send(payload.build());
        return "Ok";
    }


}
