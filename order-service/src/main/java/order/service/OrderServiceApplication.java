package order.service;


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
@ComponentScan(basePackageClasses = OrderServiceApplication.class)
@EnableBinding(Processor.class)
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
