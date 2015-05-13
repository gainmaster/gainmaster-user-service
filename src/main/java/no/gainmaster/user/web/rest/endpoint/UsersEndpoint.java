package no.gainmaster.user.web.rest.endpoint;

import no.gainmaster.user.service.User;
import no.gainmaster.user.service.UserService;
import no.gainmaster.user.web.rest.resource.UserResource;
import no.gainmaster.user.web.rest.resource.UserCollectionResource;
import no.gainmaster.user.web.rest.resource.assembler.UserResourceAssembler;

import no.gainmaster.user.web.rest.resource.assembler.UserCollectionResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@ExposesResourceFor(UserResource.class)
@RequestMapping("/users")
public class UsersEndpoint {

    @Inject
    private UserService userService;

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
        Page<User> usersPage = userService.getUsers(new PageRequest(page - 1, size));

        return new ResponseEntity<>(
            userCollectionResourceAssembler.toResource(usersPage),
            HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@Valid @RequestBody UserResource userResource) {
        //TODO: authorization (not logged in)
        User user = userService.createUser(userResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UsersEndpoint.class).getUser(user.getUsername())).toUri());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Get user resource from username
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/{username:[\\w]+}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable String username) {

        User user = userService.getUserFromUsername(username);

        return new ResponseEntity<>(
            userResourceAssembler.toResource(user),
            HttpStatus.OK
        );
    }

    /**
     * Update user resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.PATCH)
    public ResponseEntity updateUser(@PathVariable Long id) {
        //TODO: authorization
        //TODO: implement method
        return null;
    }

    /**
     * Delete user resource
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Long id) {
        //TODO: authorization
        //TODO: implement method
        return null;
    }

}
