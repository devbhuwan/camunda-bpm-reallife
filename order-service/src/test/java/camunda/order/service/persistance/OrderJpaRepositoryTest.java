package camunda.order.service.persistance;

import camunda.order.domain.Order;
import camunda.order.service.OrderServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class OrderJpaRepositoryTest {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    public void saveOrderAutoInjectCreatedByAndCreationDate() {
        orderJpaRepository.save(new Order());
    }

}