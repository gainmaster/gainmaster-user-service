package no.gainmaster.user.amqp.configuration;

import com.rabbitmq.client.ConnectionFactory;
import no.gainmaster.user.amqp.handler.AuthenticateUserHandler;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConsumerConfiguration extends RabbitServerConfiguration{

    protected final static String USER_AUTHENTICATION_ROUTING_KEY = "authentication";

    @Autowired
    private AuthenticateUserHandler authenticateUserHandler;

    @Bean
    public SimpleMessageListenerContainer createUserListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(getUserQueue());
        container.setMessageListener(new MessageListenerAdapter(authenticateUserHandler));
        container.setDefaultRequeueRejected(false);
        return container;
    }

    @Bean
    public Queue getUserQueue() {
        return amqpAdmin().declareQueue();
    }

    @Bean
    public Binding createUserQueueBinding() {
        return BindingBuilder.bind(getUserQueue()).to(userTopicExchange()).with(USER_AUTHENTICATION_ROUTING_KEY);
    }
}
