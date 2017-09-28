package camunda.event.bus.connector.praser;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Component
public class CamundaEventBusConnectorConfigurator {

    private final List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();

    @Autowired
    private MessageStartEventParseListener messageStartEventParseListener;

    public void initializeEventBusConnectorExtensions(ProcessEngineConfigurationImpl configuration) {
        bpmnParseListeners.add(messageStartEventParseListener);
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
    }


}
