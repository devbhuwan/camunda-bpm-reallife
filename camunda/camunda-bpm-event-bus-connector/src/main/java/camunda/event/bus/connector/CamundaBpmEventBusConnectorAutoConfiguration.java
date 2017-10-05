package camunda.event.bus.connector;

import camunda.event.bus.connector.message.EventSubscriptionListener;
import camunda.event.bus.connector.praser.CamundaEventBusConnectorConfigurator;
import camunda.event.channel.EventChannelAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@ConditionalOnClass(value = {EventChannelAutoConfiguration.class})
@EnableConfigurationProperties(CamundaBpmEventBusConnectorProperties.class)
@Slf4j
public class CamundaBpmEventBusConnectorAutoConfiguration extends AbstractCamundaConfiguration {

    @Autowired
    private CamundaBpmEventBusConnectorProperties properties;
    @Autowired
    private EventSubscriptionListener eventSubscriptionListener;
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        log.info("CamundaBpmEventBusConnectorAutoConfiguration #preInit() is {}", properties.isEnable());
        if (properties.isEnable()) {
            CamundaEventBusConnectorConfigurator.initializeEventBusConnectorExtensions(processEngineConfiguration, eventSubscriptionListener);
        }
    }
}
