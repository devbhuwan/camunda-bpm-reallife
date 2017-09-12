package order.service.domain;

import org.immutables.value.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Value.Immutable
public class Order {

    private Long id;
    private String orderName;
    private List<String> items;
    private LocalDateTime orderAt;

}
