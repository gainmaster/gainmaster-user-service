package no.gainmaster.user.amqp.gateway;

import no.gainmaster.user.persistence.entity.UserEntity;

/**
 * Created by lorre on 4/10/15.
 */

public interface UserGateway {

    void sendMessage(UserEntity userEntity);
}
