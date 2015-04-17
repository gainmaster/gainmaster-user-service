package no.gainmaster.user.amqp.configuration;

/**
 * Created by lorre on 4/16/15.
 */

import no.gainmaster.user.amqp.handler.GetCredentialsHandler;
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

    protected final static String USER_GET_ROUTING_KEY   = "get";

    @Autowired
    private GetCredentialsHandler credentialsHandler;

    @Bean
    public SimpleMessageListenerContainer createUserListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(getUserQueue());
        container.setMessageListener(new MessageListenerAdapter(credentialsHandler));
        return container;
    }

    @Bean
    public Queue getUserQueue(){ return amqpAdmin().declareQueue(); }

    @Bean
    public Binding createUserQueueBinding(){ return BindingBuilder.bind(getUserQueue()).to(userTopicExchange()).with(USER_GET_ROUTING_KEY); }
}
