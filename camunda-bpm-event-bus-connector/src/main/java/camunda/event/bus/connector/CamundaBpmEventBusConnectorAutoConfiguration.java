package camunda.event.bus.connector;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@EnableConfigurationProperties(CamundaBpmEventBusConnectorProperties.class)
@Slf4j
public class CamundaBpmEventBusConnectorAutoConfiguration extends AbstractCamundaConfiguration {

    @Autowired
    private CamundaBpmEventBusConnectorProperties properties;

    @PostConstruct
    public void init() {
        log.info("AutoConfiguration [{}]", CamundaBpmEventBusConnectorAutoConfiguration.class.getSimpleName());
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        log.info("CamundaBpmEventBusConnectorAutoConfiguration #preInit() is {}", properties.isEnable());
        if (properties.isEnable())
            CamundaEventBusConnectorConfigurator.initializeEventBusConnectorExtensions(processEngineConfiguration);
    }
}
