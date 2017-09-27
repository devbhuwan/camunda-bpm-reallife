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

    final List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();

    private CamundaEventBusConnectorConfigurator() {
    }

    static CamundaEventBusConnectorConfigurator configurator() {
        return new CamundaEventBusConnectorConfigurator();
    }

    CamundaEventBusConnectorConfigurator initializeEventBusConnectorExtensions(ProcessEngineConfigurationImpl configuration) {
        final CamundaEventBusConnectorConfigurator configurator = new CamundaEventBusConnectorConfigurator();
        configuration.setCustomPreBPMNParseListeners(bpmnParseListeners);
        return configurator;
    }

    CamundaEventBusConnectorConfigurator configureStartMessageEventConnector(EventChanelContext eventChanelContext, CamundaBpmStartMessageEventListenerHandler camundaBpmStartMessageEventListenerHandler) {
        bpmnParseListeners.add(new StartEventParseListener(eventChanelContext, camundaBpmStartMessageEventListenerHandler));
        return this;
    }


}
