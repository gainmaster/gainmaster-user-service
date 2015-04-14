package no.gainmaster.user.service;

import no.gainmaster.user.persistence.entity.UserEntity;
import no.gainmaster.user.persistence.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public User createUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setSalt(user.getPassword());
        userEntity.setCreated(new Date());
        userEntity = usersRepository.save(userEntity);
        return userEntity;
    }

    public User updateUser(User user) {
        //TODO: implement method
        return null;
    }

    public User deleteUser(User user) {
        //TODO: implement method
        return null;
    }

    public User getUserFromId(Long id) throws UserNotFoundException {
        User user = usersRepository.findById(id);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public User getUserFromUsername(String username) throws UserNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public User getUserFromEmail(String email) throws UserNotFoundException {
        User user = usersRepository.findByEmail(email);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public Page<User> getUsers(PageRequest pageRequest) {

        return usersRepository.findAll(pageRequest);
    }
}
