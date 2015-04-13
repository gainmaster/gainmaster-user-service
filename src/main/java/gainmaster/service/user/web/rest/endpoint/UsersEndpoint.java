package gainmaster.service.user.web.rest.endpoint;

import gainmaster.service.user.web.amqp.gateway.UserRabbitGateway;
import gainmaster.service.user.web.rest.resource.UserResource;
import gainmaster.service.user.web.rest.resource.UserCollectionResource;
import gainmaster.service.user.web.rest.resource.assembler.UserResourceAssembler;
import gainmaster.service.user.entity.UserEntity;
import gainmaster.service.user.repository.UsersRepository;

import gainmaster.service.user.web.rest.resource.assembler.UserCollectionResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@ExposesResourceFor(UserResource.class)
@RequestMapping("/users")
public class UsersEndpoint {

    @Inject
    private UsersRepository usersRepository;

    @Inject
    private UserResourceAssembler userResourceAssembler;

    @Inject
    private UserCollectionResourceAssembler userCollectionResourceAssembler;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserCollectionResource> getUsers(
        @RequestParam(defaultValue = "15") Integer size,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(required = false) String query
    ) {

        Page<UserEntity> users = usersRepository.findAll(new PageRequest(
            page - 1,
            size
        ));

        return new ResponseEntity<UserCollectionResource>(
            userCollectionResourceAssembler.toResource(users),
            HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@Valid @RequestBody UserResource userResource) {
        //TODO: authorization (not logged in)
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userResource.getName());
        userEntity.setUsername(userResource.getUsername());
        userEntity.setEmail(userResource.getEmail());
        userEntity.setPassword(userResource.getPassword());
        userEntity.setSalt(userResource.getPassword());
        userEntity.setCreated(new Date());
        userEntity = usersRepository.save(userEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UsersEndpoint.class).getUser(userEntity.getId())).toUri());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Get user resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable Long id) {
        return Optional.ofNullable(usersRepository.findOne(id))
            .map(userEntity -> new ResponseEntity<UserResource>(
                userResourceAssembler.toResource(userEntity),
                HttpStatus.OK
            ))
            .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /**
     * Update user resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity updateUser(@PathVariable Long id) {
        //TODO: authorization
        UserEntity userEntity = usersRepository.findOne(id);

        if (userEntity == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return null;
    }

    /**
     * Delete user resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Long id) {
        //TODO: authorization
        UserEntity userEntity = usersRepository.findOne(id);

        if (userEntity == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        usersRepository.delete(userEntity);
        //TODO: notify other services of deletion (message-bus)
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
