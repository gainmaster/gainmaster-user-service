package gainmaster.service.user.web.amqp.configuration;

import gainmaster.service.user.web.amqp.util.HttpConnectionUtility;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lorre on 4/10/15.
 */

@Configuration
public abstract class RabbitServerConfiguration {

    protected final static String DEFAULT_HOSTNAME      = "localhost";
    protected final static String USERNAME              = "guest";
    protected final static String PASSWORD              = "guest";
    protected final static String RABBITMQ_ETCD_PATH    = "http://127.0.0.1:4001/v2/keys/registrator/rabbitmq/1";
    protected final int port                            = 5672;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory;

        String hostname = HttpConnectionUtility.getJSONNode(RABBITMQ_ETCD_PATH, "value");
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
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}
}
