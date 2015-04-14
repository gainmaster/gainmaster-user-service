package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.persistence.entity.UserEntity;
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
