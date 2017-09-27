package camunda.event.bus.connector;

import camunda.event.channel.EventChannelAutoConfiguration;
import camunda.event.channel.contracts.EventChanelContext;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Configuration
@ConditionalOnClass(value = {
        EventChannelAutoConfiguration.class

})
@ConditionalOnBean(value = EventChanelContext.class)
@EnableConfigurationProperties(CamundaBpmEventBusConnectorProperties.class)
@Slf4j
public class CamundaBpmEventBusConnectorAutoConfiguration extends AbstractCamundaConfiguration {

    @Autowired
    private CamundaBpmEventBusConnectorProperties properties;
    @Autowired
    private EventChanelContext eventChanelContext;
    @Autowired
    private CamundaBpmStartMessageEventListenerHandler camundaBpmStartMessageEventListenerHandler;

    @PostConstruct
    public void init() {
        log.info("AutoConfiguration [{}]", CamundaBpmEventBusConnectorAutoConfiguration.class.getSimpleName());
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        log.info("CamundaBpmEventBusConnectorAutoConfiguration #preInit() is {}", properties.isEnable());
        if (properties.isEnable()) {
            CamundaEventBusConnectorConfigurator.configurator()
                    .initializeEventBusConnectorExtensions(processEngineConfiguration)
                    .configureStartMessageEventConnector(eventChanelContext, camundaBpmStartMessageEventListenerHandler);
        }
    }
}
