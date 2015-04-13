package gainmaster.service.user.web.amqp.gateway;

import gainmaster.service.user.entity.UserEntity;
import gainmaster.service.user.web.amqp.configuration.UserRabbitServerConfiguration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by lorre on 4/10/15.
 */

@Component
public class UserRabbitGateway extends RabbitGatewaySupport implements UserGateway {
    public void sendMessage(UserEntity userEntity){

        //Get external configuration
        ApplicationContext context =
            new AnnotationConfigApplicationContext(UserRabbitServerConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        //Send message
        template.convertAndSend(userEntity);
    }
}
