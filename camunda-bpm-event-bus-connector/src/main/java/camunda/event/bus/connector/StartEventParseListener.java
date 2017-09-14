package camunda.event.bus.connector;

import event.channel.contracts.EventChanelContext;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;


/**
 * @author Bhuwan Prasad Upadhyay
 */

@Slf4j
class StartEventParseListener extends AbstractBpmnParseListener {

    private EventChanelContext eventChanelContext;

    StartEventParseListener(EventChanelContext eventChanelContext) {
        this.eventChanelContext = eventChanelContext;
    }

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        log.info("Start Event");
        eventChanelContext.registerListener();
    }


}
