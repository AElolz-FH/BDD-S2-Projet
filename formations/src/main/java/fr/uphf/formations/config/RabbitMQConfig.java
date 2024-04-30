package fr.uphf.formations.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_EXCHANGE_NAME = "userExchange";
    public static final String USER_QUEUE_NAME = "userQueue";
    public static final String USER_ROUTING_KEY = "user.deleted";


    public static final String FORMATION_EXCHANGE_NAME = "formationExchange";
    public static final String FORMATION_QUEUE_NAME = "formationQueue";
    public static final String FORMATION_ROUTING_KEY = "formation.deleted";

    public static final String SEANCE_EXCHANGE_NAME = "seanceExchange";
    public static final String SEANCE_QUEUE_NAME = "seanceQueue";
    public static final String SEANCE_ROUTING_KEY = "seance.created";

    @Bean
    Queue userQueue() {
        return new Queue(USER_QUEUE_NAME, true);
    }

    @Bean
    TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE_NAME);
    }

    @Bean
    Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(USER_ROUTING_KEY);
    }

    @Bean
    Queue formationQueue() {
        return new Queue(FORMATION_QUEUE_NAME, true);
    }

    @Bean
    TopicExchange formationExchange() {
        return new TopicExchange(FORMATION_EXCHANGE_NAME);
    }

    @Bean
    Binding formationBinding(Queue formationQueue, TopicExchange formationExchange) {
        return BindingBuilder.bind(formationQueue).to(formationExchange).with(FORMATION_ROUTING_KEY);
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
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(FORMATION_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(3);
        factory.setConcurrentConsumers(1);
        return factory;
    }
}
