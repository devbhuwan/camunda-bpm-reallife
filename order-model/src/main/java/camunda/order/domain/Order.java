package camunda.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private String orderName;
    private List<String> items;
    private LocalDateTime orderAt;
    private String processInstanceId;

}
