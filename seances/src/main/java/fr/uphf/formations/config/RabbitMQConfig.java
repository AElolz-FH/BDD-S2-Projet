package fr.uphf.formations.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "seanceUpdateQueue";
    public static final String SALLE_QUEUE_NAME = "salleUpdateQueue";
    public static final String EXCHANGE_NAME = "formationExchange";
    public static final String ROUTING_KEY = "formation.deleted";
    public static final String SALLE_ROUTING_KEY = "salle.deleted";

    @Bean
    Queue formationQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    Queue salleQueue() {
        return new Queue(SALLE_QUEUE_NAME, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding formationBinding(Queue formationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(formationQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Binding salleBinding(Queue salleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(salleQueue).to(exchange).with(SALLE_ROUTING_KEY);
    }
}
