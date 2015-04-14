package no.gainmaster.user.amqp.configuration;

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

    protected final static String DEFAULT_HOSTNAME      = "10.0.0.102";
    protected final static int    DEFAULT_PORT          = 49156;
    protected final static String USERNAME              = "bachelorthesis";
    protected final static String PASSWORD              = "bachelorthesis";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(DEFAULT_HOSTNAME);
        connectionFactory.setPort(DEFAULT_PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}
}
