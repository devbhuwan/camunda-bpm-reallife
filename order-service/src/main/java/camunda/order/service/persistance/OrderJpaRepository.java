package camunda.order.service.persistance;

import camunda.order.domain.Order;

public interface OrderJpaRepository extends SecurityAwareJpaRepository<Order, Long> {

}
