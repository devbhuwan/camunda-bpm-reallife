package camunda.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Data
@NoArgsConstructor
public class Order implements Serializable {

    private Long id;
    private String itemName;
    private Integer quantity;
    private LocalDateTime orderAt;
    private String processInstanceId;

    public Order(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.orderAt = LocalDateTime.now();
    }
}
