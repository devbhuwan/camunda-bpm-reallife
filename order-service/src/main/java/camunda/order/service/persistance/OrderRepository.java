package camunda.order.service.persistance;

import camunda.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class OrderRepository {

    private final AtomicLong idAtomic = new AtomicLong(10000);
    private Map<Long, Order> orders = new ConcurrentHashMap<>();

    public void placeOrder(Order order) {
        order.setId(idAtomic.incrementAndGet());
        orders.put(order.getId(), order);
    }

    public Collection<Order> findAll() {
        return orders.values();
    }

}
