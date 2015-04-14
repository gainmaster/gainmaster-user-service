package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.persistence.entity.UserEntity;
import no.gainmaster.user.service.User;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;


/**
 * Created by lorre on 4/10/15.
 */

public class UserRabbitGateway extends RabbitGatewaySupport implements UserGateway {

    private String routingKey = "";

    public void sendMessage(User userEntity){

        //Send message
        getRabbitTemplate().convertSendAndReceive(routingKey, userEntity.toString());
        System.out.println("RABBITMQ: Sent message with key=" + routingKey + ": " + userEntity);
    }

    public void setRoutingKey(String routingKey){
        this.routingKey = routingKey;
    }
}
