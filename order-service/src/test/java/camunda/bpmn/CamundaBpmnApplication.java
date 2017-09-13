package camunda.bpmn;


import bpmntoconstant.generator.annotations.EnableBpmnMetadataConstantGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@EnableBpmnMetadataConstantGenerator
@SpringBootApplication
@ComponentScan(basePackageClasses = CamundaBpmnApplication.class)
@EnableBinding(Processor.class)
public class CamundaBpmnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaBpmnApplication.class, args);
    }

}
