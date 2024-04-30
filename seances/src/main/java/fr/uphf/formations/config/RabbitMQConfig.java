package fr.uphf.formations.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "seanceUpdateQueue";
    public static final String SALLE_QUEUE_NAME = "salleUpdateQueue";
    public static final String EXCHANGE_NAME = "formationExchange";
    public static final String ROUTING_KEY = "formation.deleted";
    public static final String SALLE_ROUTING_KEY = "salle.deleted";
    public static final String SEANCE_EXCHANGE_NAME = "seanceExchange";
    public static final String SEANCE_QUEUE_NAME = "seanceQueue";
    public static final String SEANCE_ROUTING_KEY = "seance.created";


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

    @Bean
    Queue seanceQueue() {
        return new Queue(SEANCE_QUEUE_NAME, true);
    }

    @Bean
    TopicExchange seanceExchange() {
        return new TopicExchange(SEANCE_EXCHANGE_NAME);
    }

    @Bean
    Binding seanceBinding(Queue seanceQueue, TopicExchange seanceExchange) {
        return BindingBuilder.bind(seanceQueue).to(seanceExchange).with(SEANCE_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate seanceRabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(SEANCE_EXCHANGE_NAME);
        return rabbitTemplate;
    }
}
