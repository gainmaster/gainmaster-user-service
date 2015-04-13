package gainmaster.service.user.web.amqp.configuration;

import gainmaster.service.user.web.amqp.handler.UserReplyHandler;
import org.springframework.amqp.core.DirectExchange;
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

public class UserRabbitServerConfiguration extends RabbitServerConfiguration{

    protected final static String USER_EXCHANGE_NAME    = "gainmaster.user.exchange";
    protected final static String USER_QUEUE_NAME       = "gainmaster.user.queue";
    protected final static String USER_REPLY_QUEUE_NAME = "gainmaster.user.reply.queue";

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        template.setExchange(USER_EXCHANGE_NAME);
        template.setQueue(USER_QUEUE_NAME);
        template.setReplyQueue(replyUserQueue());
        template.setReplyTimeout(60000);
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
    public MessageConverter jsonMessageConverter() { return new JsonMessageConverter();}

    @Bean
    public DirectExchange userDataExchange() { return new DirectExchange(USER_EXCHANGE_NAME);}

    @Bean
    public Queue userQueue() { return new Queue(USER_QUEUE_NAME);}

    @Bean
    public Queue replyUserQueue(){ return new Queue(USER_REPLY_QUEUE_NAME);}
}
