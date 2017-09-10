package order.service;


import bpmntoconstant.generator.annotations.EnableBpmnMetadataConstantGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 *
 * @author Bhuwan Prasad Upadhyay
 */
@EnableBpmnMetadataConstantGenerator
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
