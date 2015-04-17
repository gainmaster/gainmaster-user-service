package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.service.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;

import java.io.IOException;


/**
 * Created by lorre on 4/10/15.
 */

public class UserRabbitGateway extends RabbitGatewaySupport implements UserGateway {

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public void sendMessage(String routingKey, User userEntity){
        try {

            //Send message
            getRabbitTemplate().convertAndSend(routingKey, ow.writeValueAsString(userEntity));

            System.out.println("RABBITMQ: Sent message with key " + routingKey + ": \n" + ow.writeValueAsString(userEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
