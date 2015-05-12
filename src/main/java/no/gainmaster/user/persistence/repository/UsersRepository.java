package no.gainmaster.user.persistence.repository;

import no.gainmaster.user.persistence.entity.UserEntity;
import no.gainmaster.user.service.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    Page<User> findAll(Pageable pageable);

    UserEntity findById(Long id);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

}
