package camunda.bpmn.scenario;

import camunda.bpmn.bpmn.metadata.ProcessCallActivityConstants;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Component
public class CallActivityServiceTask implements JavaDelegate {

    @Autowired
    private HelloService helloService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (helloService.isHello()) {
            throw new BpmnError(ProcessCallActivityConstants.Ids.CALL_ACTIVITY);
        }
    }

}
