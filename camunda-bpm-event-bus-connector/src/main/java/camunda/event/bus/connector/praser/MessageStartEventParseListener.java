package camunda.event.bus.connector.praser;

import camunda.event.bus.connector.message.CamundaEventBusConnectorMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

import static camunda.event.bus.connector.message.CamundaEventBusConnectorMessageListener.START_MESSAGE_EVENT;


/**
 * @author Bhuwan Prasad Upadhyay
 */

@Component
@Slf4j
public class MessageStartEventParseListener extends AbstractBpmnParseListener {

    @Autowired
    @Lazy
    private CamundaEventBusConnectorMessageListener camundaEventBusConnectorMessageListener;

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        Map<String, EventSubscriptionDeclaration> eventDefinitions = (Map<String, EventSubscriptionDeclaration>) scope.getProcessDefinition().getProperty("eventDefinitions");
        EventSubscriptionDeclaration eventSubscriptionDeclaration = eventDefinitions.get(startEventActivity.getId());
        if (eventSubscriptionDeclaration != null) {
            String messageName = eventSubscriptionDeclaration.getUnresolvedEventName();
            if (StringUtils.isNotEmpty(messageName)) {
                camundaEventBusConnectorMessageListener.registerEndpointMessageEventName(START_MESSAGE_EVENT, messageName);
            }
        }
    }


}
