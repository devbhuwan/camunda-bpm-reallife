package order.service;


import bpmntoconstant.generator.annotations.EnableBpmnMetadataConstantGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@EnableBpmnMetadataConstantGenerator
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @EnableBinding(Processor.class)
    public static class MyProcessor {

        @Autowired
        private Processor channels;

        @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
        public String transform(String in) {
            return in + " world";
        }
    }

}
