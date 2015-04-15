package no.gainmaster.user.amqp.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

/**
 * Created by lorre on 4/10/15.
 */

@Configuration
public abstract class RabbitServerConfiguration implements EnvironmentAware{

    protected final static String HOSTNAME_KEY = "hostname";
    protected final static String PORT_KEY     = "port";
    protected final static String USERNAME_KEY = "username";
    protected final static String PASSWORD_KEY = "password";

    private Environment environment;
    private PropertyResolver propertyResolver;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.amqp.");
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(propertyResolver.getProperty(HOSTNAME_KEY));
        connectionFactory.setPort(Integer.parseInt(propertyResolver.getProperty(PORT_KEY)));
        connectionFactory.setUsername(propertyResolver.getProperty(USERNAME_KEY));
        connectionFactory.setPassword(propertyResolver.getProperty(PASSWORD_KEY));
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}
}
