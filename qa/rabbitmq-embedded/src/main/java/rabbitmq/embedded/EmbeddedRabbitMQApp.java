package rabbitmq.embedded;

import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;

public class EmbeddedRabbitMQApp {

    public static void main(String[] args) {
        EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder().build();
        EmbeddedRabbitMq rabbitMq = new EmbeddedRabbitMq(config);
        rabbitMq.start();
    }

}
