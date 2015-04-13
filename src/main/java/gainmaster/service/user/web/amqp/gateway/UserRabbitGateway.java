package gainmaster.service.user.web.amqp.gateway;

import gainmaster.service.user.entity.UserEntity;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;


/**
 * Created by lorre on 4/10/15.
 */

public class UserRabbitGateway extends RabbitGatewaySupport implements UserGateway {

    public void sendMessage(UserEntity userEntity){

        //Send message
        getRabbitTemplate().convertAndSend(userEntity);
    }
}
