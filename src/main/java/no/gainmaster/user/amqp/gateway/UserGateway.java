package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.persistence.entity.UserEntity;
import no.gainmaster.user.service.User;

public interface UserGateway {

    void sendMessage(String routingKey, User userEntity);

}
