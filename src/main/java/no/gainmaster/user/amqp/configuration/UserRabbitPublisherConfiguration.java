package no.gainmaster.user.amqp.configuration;

import no.gainmaster.user.amqp.gateway.UserRabbitGateway;
import no.gainmaster.user.amqp.handler.UserReplyHandler;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by lorre on 4/13/15.
 */

@Configuration
public class UserRabbitPublisherConfiguration extends RabbitServerConfiguration{

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setExchange(USER_EXCHANGE_NAME);
        template.setReplyQueue(userReplyQueue());
        template.setReplyTimeout(REPLY_TIMEOUT);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(userReplyQueue());
        container.setMessageListener(new MessageListenerAdapter(new UserReplyHandler()));
        return container;
    }

    @Bean
    public UserRabbitGateway userRabbitGateway(){
        UserRabbitGateway userRabbitGateway = new UserRabbitGateway();
        userRabbitGateway.setRabbitTemplate(rabbitTemplate());
        return userRabbitGateway;
    }

    @Bean
    public Queue userReplyQueue(){ return amqpAdmin().declareQueue();}
}
