package camunda.event.bus.connector;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public class CamundaEventBusConnectorConfigurator {

    public static void initializeEventBusConnectorExtensions(ProcessEngineConfigurationImpl configuration) {
        List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();
        bpmnParseListeners.add(new StartEventParseListener());
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
    }

}
