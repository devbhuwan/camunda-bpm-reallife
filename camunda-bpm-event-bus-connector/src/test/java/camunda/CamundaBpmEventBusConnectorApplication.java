package camunda;

import bpmntoconstant.generator.annotations.EnableBpmnMetadataConstantGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableBpmnMetadataConstantGenerator
@SpringBootApplication
@ComponentScan(basePackageClasses = ApplicationRoot.class)
public class CamundaBpmEventBusConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaBpmEventBusConnectorApplication.class, args);
    }

}
