package gainmaster.service.user.web.amqp.gateway;

import gainmaster.service.user.entity.UserEntity;

/**
 * Created by lorre on 4/10/15.
 */

public interface UserDataGateway {
    void sendUserData(UserEntity userEntity);
}
