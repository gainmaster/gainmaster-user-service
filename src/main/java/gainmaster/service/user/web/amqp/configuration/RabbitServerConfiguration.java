package gainmaster.service.user.web.amqp.configuration;

import gainmaster.service.user.web.amqp.util.HttpConnection;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lorre on 4/10/15.
 */

@Configuration
public class RabbitServerConfiguration {

    protected final static String DEFAULT_HOSTNAME = "localhost";
    protected final static String USERNAME = "guest";
    protected final static String PASSWORD = "guest";
    protected final static String RABBITMQ_ETCD_PATH = "http://127.0.0.1:4001/v2/keys/registrator/rabbitmq/1";

    protected final static String USER_EXCHANGE_NAME = "gainmaster.user.exchange";
    protected final static String USER_QUEUE_NAME = "gainmaster.user.queue";

    @Value("${amqp.port:5672}")
    private final int port = 5672;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory;
        String hostname = HttpConnection.getResponse(RABBITMQ_ETCD_PATH);
        if(hostname == null){
            connectionFactory = new CachingConnectionFactory(DEFAULT_HOSTNAME);
        }else{
            connectionFactory = new CachingConnectionFactory(hostname);
        }

        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setPort(port);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        template.setExchange(USER_EXCHANGE_NAME);
        template.setQueue(USER_QUEUE_NAME);
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() { return new JsonMessageConverter();}

    @Bean
    public DirectExchange userDataExchange() { return new DirectExchange(USER_EXCHANGE_NAME);}

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}

    @Bean
    public Queue userQueue() { return new Queue(USER_QUEUE_NAME);}
}
