package fr.uphf.formations.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME_SALLE = "salleUpdateQueue";
    public static final String EXCHANGE_NAME = "formationExchange";
    public static final String ROUTING_KEY_SALLE_DELETED = "salle.deleted";



    @Bean
    Queue salleQueue() {
        return new Queue(QUEUE_NAME_SALLE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding salleBinding(Queue salleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(salleQueue).to(exchange).with(ROUTING_KEY_SALLE_DELETED);
    }
}
