package camunda.event.bus.connector;

import camunda.event.channel.contracts.EventChanelContext;
import camunda.event.channel.contracts.ImmutableEventListenerSpec;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.Map;


/**
 * @author Bhuwan Prasad Upadhyay
 */

@Slf4j
class StartEventParseListener extends AbstractBpmnParseListener {

    private EventChanelContext eventChanelContext;
    private CamundaBpmStartMessageEventListenerHandler camundaBpmStartMessageEventListenerHandler;

    StartEventParseListener(EventChanelContext eventChanelContext, CamundaBpmStartMessageEventListenerHandler camundaBpmStartMessageEventListenerHandler) {
        this.eventChanelContext = eventChanelContext;
        this.camundaBpmStartMessageEventListenerHandler = camundaBpmStartMessageEventListenerHandler;
    }

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        Map<String, EventSubscriptionDeclaration> eventDefinitions = (Map<String, EventSubscriptionDeclaration>) scope.getProcessDefinition().getProperty("eventDefinitions");
        EventSubscriptionDeclaration eventSubscriptionDeclaration = eventDefinitions.get(startEventActivity.getId());
        if (eventSubscriptionDeclaration != null) {
            String messageName = eventSubscriptionDeclaration.getUnresolvedEventName();
            eventChanelContext.registerListener(ImmutableEventListenerSpec.builder()
                    .message(messageName)
                    .handleEventBean(camundaBpmStartMessageEventListenerHandler)
                    .build()
            );
        }
    }


}
