package camunda.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Data
@NoArgsConstructor
public class Order {

    private Long id;
    private String itemName;
    private Integer quantity;
    private LocalDateTime orderAt;
    private String processInstanceId;

    public Order(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}
