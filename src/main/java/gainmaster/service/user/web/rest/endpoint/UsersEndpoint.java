package gainmaster.service.user.web.rest.endpoint;

import gainmaster.service.user.web.amqp.gateway.RabbitUserDataGateway;
import gainmaster.service.user.web.rest.resource.UserResource;
import gainmaster.service.user.web.rest.resource.UsersResource;
import gainmaster.service.user.web.rest.resource.assembler.UserResourceAssembler;
import gainmaster.service.user.entity.UserEntity;
import gainmaster.service.user.repository.UsersRepository;

import gainmaster.service.user.web.rest.resource.assembler.UsersResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@ExposesResourceFor(UserResource.class)
@RequestMapping("/users")
public class UsersEndpoint {

    @Autowired
    RabbitUserDataGateway rabbitUserDataGateway;

    @Inject
    UsersRepository usersRepository;

    @Inject
    UserResourceAssembler userResourceAssembler;

    @Inject
    UsersResourceAssembler usersResourceAssembler;

    @Inject
    EntityLinks entityLinks;


    /**
     * Get single user
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
     * Add single user
     *
     * @param userResource
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addUser(@Valid @RequestBody UserResource userResource) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userResource.getUsername());
        userEntity.setPassword(userResource.getPassword());
        userEntity = usersRepository.save(userEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UsersEndpoint.class).getUser(userEntity.getId())).toUri());

        //Send AMQP message
        rabbitUserDataGateway.sendUserData(userEntity);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Get users collection
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UsersResource> getUsers(
        @RequestParam(defaultValue = "10") String size,
        @RequestParam(defaultValue = "1") String page
    ) {
        Page<UserEntity> users = usersRepository.findAll(new PageRequest(
            (Integer.parseInt(page) - 1), //TODO: Remove brackets.
            Integer.parseInt(size)
        ));

        return new ResponseEntity<UsersResource>(
            usersResourceAssembler.toResource(users),
            HttpStatus.OK
        );
    }
}
