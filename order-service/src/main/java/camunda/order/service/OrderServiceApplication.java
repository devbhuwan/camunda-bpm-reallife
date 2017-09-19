package camunda.order.service;


import bpmntoconstant.generator.annotations.EnableBpmnMetadataConstantGenerator;
import camunda.ApplicationRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@EnableBpmnMetadataConstantGenerator
@SpringBootApplication
@ComponentScan(basePackageClasses = ApplicationRoot.class)
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
