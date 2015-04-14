package gainmaster.service.user.amqp.gateway;

import gainmaster.service.user.persistence.entity.UserEntity;

/**
 * Created by lorre on 4/10/15.
 */

public interface UserGateway {

    void sendMessage(UserEntity userEntity);
}
