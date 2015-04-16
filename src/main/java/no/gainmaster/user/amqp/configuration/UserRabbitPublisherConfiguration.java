package no.gainmaster.user.amqp.configuration;

import no.gainmaster.user.amqp.gateway.UserRabbitGateway;
import no.gainmaster.user.amqp.handler.UserReplyHandler;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lorre on 4/13/15.
 */

@Configuration
public class UserRabbitPublisherConfiguration extends RabbitServerConfiguration{

    protected final static String USER_REPLY_QUEUE_NAME = "gainmaster.user.queue.reply";

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setExchange(USER_EXCHANGE_NAME);
        template.setReplyQueue(userReplyQueue());
        template.setReplyTimeout(0);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(USER_REPLY_QUEUE_NAME);
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
    public Queue userReplyQueue(){ return new Queue(USER_REPLY_QUEUE_NAME);}


}
