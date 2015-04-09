package gainmaster.service.user.repository;

import gainmaster.service.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    Page<UserEntity> findAll(Pageable pageable);

}
