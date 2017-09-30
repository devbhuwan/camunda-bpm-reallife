package camunda.event.bus.connector.praser;

import camunda.event.bus.connector.message.EventSubscriptionListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public class CamundaEventBusConnectorConfigurator {

    public static void initializeEventBusConnectorExtensions(ProcessEngineConfigurationImpl configuration, EventSubscriptionListener eventSubscriptionListener) {
        List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();
        bpmnParseListeners.add(new MessageEventDefinitionParseListener(eventSubscriptionListener));
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
    }


}
