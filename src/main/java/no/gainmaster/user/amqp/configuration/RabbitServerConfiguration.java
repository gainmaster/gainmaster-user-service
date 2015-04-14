package no.gainmaster.user.amqp.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

/**
 * Created by lorre on 4/10/15.
 */

@Configuration
public abstract class RabbitServerConfiguration implements EnvironmentAware{

    protected final static String DEFAULT_HOSTNAME      = "10.0.0.102";
    protected final static int    DEFAULT_PORT          = 49156;
    protected final static String USERNAME              = "bachelorthesis";
    protected final static String PASSWORD              = "bachelorthesis";

    private Environment environment;
    private PropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "");
    }

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
