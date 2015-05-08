package no.gainmaster.user.amqp.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
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

@Configuration
public class RabbitServerConfiguration { //implements EnvironmentAware{

    //KEYS
    protected final static String HOSTNAME_KEY = "hostname";
    protected final static String PORT_KEY     = "port";
    protected final static String USERNAME_KEY = "username";
    protected final static String PASSWORD_KEY = "password";
    protected final static String SPRING_AMQP_PREFIX = "spring.amqp.";

    //EXCHANGE NAMES
    protected final static String USER_EXCHANGE_NAME = "gainmaster.user.exchange.topic";

    //AMQP SETTINGS
    protected final static int REPLY_TIMEOUT = 2500;

    /*
    private Environment environment;
    private PropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, SPRING_AMQP_PREFIX);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(propertyResolver.getProperty(HOSTNAME_KEY));
        connectionFactory.setPort(Integer.parseInt(propertyResolver.getProperty(PORT_KEY)));
        connectionFactory.setUsername(propertyResolver.getProperty(USERNAME_KEY));
        connectionFactory.setPassword(propertyResolver.getProperty(PASSWORD_KEY));
        return connectionFactory;
    }*/

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("amqp.service.hesjevik.im");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("gainmaster");
        connectionFactory.setPassword("gainmaster");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory());}

    @Bean
    public TopicExchange userTopicExchange() { return new TopicExchange(USER_EXCHANGE_NAME);}
}
