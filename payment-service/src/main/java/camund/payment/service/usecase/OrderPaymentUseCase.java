package camund.payment.service.usecase;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPaymentUseCase implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Delegate Order Payment [{}]", delegateExecution.getVariable("order"));
    }
}
