package no.gainmaster.user.amqp.configuration;

import no.gainmaster.user.amqp.gateway.UserRabbitGateway;
import no.gainmaster.user.amqp.handler.UserReplyHandler;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

/**
 * Created by lorre on 4/13/15.
 */

//@Configuration
public class UserRabbitServerConfiguration extends RabbitServerConfiguration{

    protected final static String USER_EXCHANGE_NAME    = "gainmaster.user.exchange";
    protected final static String USER_REPLY_QUEUE_NAME = "gainmaster.user.reply.queue";

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        configureTemplate(template);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(replyUserQueue());
        container.setMessageListener(new MessageListenerAdapter(new UserReplyHandler()));
        return container;
    }

    @Bean
    UserRabbitGateway userRabbitGateway(){
        UserRabbitGateway userRabbitGateway = new UserRabbitGateway();
        userRabbitGateway.setRabbitTemplate(rabbitTemplate());
        return userRabbitGateway;
    }

    @Bean
    public MessageConverter jsonMessageConverter() { return new JsonMessageConverter();}

    @Bean
    public FanoutExchange userDataExchange() { return new FanoutExchange(USER_EXCHANGE_NAME);}

    @Bean
    public Queue replyUserQueue(){ return new Queue(USER_REPLY_QUEUE_NAME);}

    private void configureTemplate(RabbitTemplate template){
        template.setMessageConverter(jsonMessageConverter());
        template.setExchange(USER_EXCHANGE_NAME);
        template.setReplyQueue(replyUserQueue());
        template.setReplyTimeout(60000);
    }
}
