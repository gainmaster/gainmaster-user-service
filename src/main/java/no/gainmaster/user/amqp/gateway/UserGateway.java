package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.persistence.entity.UserEntity;
import no.gainmaster.user.service.User;

/**
 * Created by lorre on 4/10/15.
 */

public interface UserGateway {

    void sendMessage(String routingKey, User userEntity);

}
