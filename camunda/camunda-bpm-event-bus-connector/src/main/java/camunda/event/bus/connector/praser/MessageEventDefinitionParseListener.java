package camunda.event.bus.connector.praser;

import camunda.event.bus.connector.message.EventSubscriptionListener;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.camunda.bpm.engine.impl.core.model.PropertyKey;
import org.camunda.bpm.engine.impl.event.EventType;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Bhuwan Prasad Upadhyay
 */

public class MessageEventDefinitionParseListener extends AbstractBpmnParseListener {

    private EventSubscriptionListener eventSubscriptionListener;

    public MessageEventDefinitionParseListener(EventSubscriptionListener eventSubscriptionListener) {
        this.eventSubscriptionListener = eventSubscriptionListener;
    }

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        Map<String, EventSubscriptionDeclaration> eventDefinitions = (Map<String, EventSubscriptionDeclaration>) scope.getProcessDefinition().getProperty("eventDefinitions");
        EventSubscriptionDeclaration eventSubscriptionDeclaration = eventDefinitions.get(startEventActivity.getId());
        if (eventSubscriptionDeclaration != null) {
            String messageName = eventSubscriptionDeclaration.getUnresolvedEventName();
            if (StringUtils.isNotEmpty(messageName)) {
                eventSubscriptionListener.register(messageName);
            }
        }
    }


    @Override
    public void parseReceiveTask(Element receiveTaskElement, ScopeImpl scope, ActivityImpl activity) {
        Set<String> eventDefinitions = getEventDefinitions(activity);
        eventDefinitions.forEach(messageName -> {
            if (StringUtils.isNotEmpty(messageName)) {
                eventSubscriptionListener.register(messageName);
            }
        });
    }

    @Override
    public void parseIntermediateMessageCatchEventDefinition(Element messageEventDefinition, ActivityImpl nestedActivity) {
        Set<String> eventDefinitions = getEventDefinitions(nestedActivity);
        eventDefinitions.forEach(messageName -> {
            if (StringUtils.isNotEmpty(messageName)) {
                eventSubscriptionListener.register(messageName);
            }
        });
    }

    private Set<String> getEventDefinitions(ActivityImpl activity) {
        return ((HashMap<String, EventSubscriptionDeclaration>) activity.getProperties().get(new PropertyKey<>("eventDefinitions")))
                .values().stream()
                .filter(sub -> EventType.MESSAGE.name().equals(sub.getEventType()))
                .map(EventSubscriptionDeclaration::getUnresolvedEventName)
                .collect(Collectors.toSet());
    }
}
