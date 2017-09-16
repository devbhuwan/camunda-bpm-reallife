package camunda.event.bus.connector;

import camunda.event.channel.contracts.EventChanelContext;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
class CamundaEventBusConnectorConfigurator {

    static void initializeEventBusConnectorExtensions(ProcessEngineConfigurationImpl configuration, EventChanelContext eventChanelContext) {
        List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();
        bpmnParseListeners.add(new StartEventParseListener(eventChanelContext));
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
    }

}
