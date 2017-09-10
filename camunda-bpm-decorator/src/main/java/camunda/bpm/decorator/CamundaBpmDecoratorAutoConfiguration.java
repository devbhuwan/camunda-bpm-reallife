package camunda.bpm.decorator;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@EnableConfigurationProperties(CamundaBpmDecoratorProperties.class)
@Slf4j
public class CamundaBpmDecoratorAutoConfiguration {

    @Autowired
    private CamundaBpmDecoratorProperties properties;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @PostConstruct
    private void configure() {
        log.info("AutoConfiguration [{0}]", CamundaBpmDecoratorAutoConfiguration.class.getSimpleName());
        if (properties.isEnable()) {
            List<BpmnParseListener> bpmnParseListeners = new ArrayList<>();
            bpmnParseListeners.add(new TaskParseListener());
            ((ProcessEngineConfigurationImpl) processEngineConfiguration).setCustomPreBPMNParseListeners(bpmnParseListeners);
        }
    }
}
