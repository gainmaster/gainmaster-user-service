package no.gainmaster.user.amqp.configuration;

import no.gainmaster.user.amqp.util.HttpConnectionUtility;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;

/**
 * Created by lorre on 4/10/15.
 */

//@Configuration
public abstract class RabbitServerConfiguration {

    protected final static String DEFAULT_HOSTNAME      = "localhost";
    protected final static int    DEFAULT_PORT          = 5672;
    protected final static String USERNAME              = "guest";
    protected final static String PASSWORD              = "guest";
    protected final static String RABBITMQ_ETCD_PATH    = "http://172.17.42.1:4001/v2/keys/registrator/rabbitmq/1";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory;
        String hostname = HttpConnectionUtility.getRegisteredNodeAddress(RABBITMQ_ETCD_PATH);

        if(hostname == null){
            connectionFactory = new CachingConnectionFactory(DEFAULT_HOSTNAME);
            connectionFactory.setPort(DEFAULT_PORT);
        }else{
            connectionFactory = new CachingConnectionFactory(hostname.split(":")[0]);
            connectionFactory.setPort(Integer.parseInt(hostname.split(":")[1]));
        }

        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}
}
