package camunda.bpm.decorator;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
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
public class CamundaBpmDecoratorAutoConfiguration extends AbstractCamundaConfiguration {

    @Autowired
    private CamundaBpmDecoratorProperties properties;

    @PostConstruct
    private void configure() {
        log.info("AutoConfiguration [{}]", CamundaBpmDecoratorAutoConfiguration.class.getSimpleName());

    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (properties.isEnable()) {
            CamundaBpmDecoratorConfigurator.initializeBpmDecoratorExtensions(processEngineConfiguration);
        }
    }
}