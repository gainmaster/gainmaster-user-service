package gainmaster.service.user.persistence.repository;

import gainmaster.service.user.persistence.entity.UserEntity;
import gainmaster.service.user.service.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    Page<User> findAll(Pageable pageable);

    UserEntity findOneByUsername(String username);

    UserEntity findOneByEmail(String email);

}
