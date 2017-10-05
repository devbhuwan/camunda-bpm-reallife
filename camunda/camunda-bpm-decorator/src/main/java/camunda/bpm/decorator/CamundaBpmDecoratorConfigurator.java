package camunda.bpm.decorator;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public class CamundaBpmDecoratorConfigurator {

    public static void initializeBpmDecoratorExtensions(ProcessEngineConfigurationImpl configuration) {
        List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();
        bpmnParseListeners.add(new TaskParseListener());
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
    }

}
