package camunda;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RetrievePaymentCommandPayload {

    private Long refId;
    private String reason;
    private BigDecimal amount;

    public RetrievePaymentCommandPayload(Long refId, BigDecimal amount) {
        this.refId = refId;
        this.amount = amount;
    }
}
