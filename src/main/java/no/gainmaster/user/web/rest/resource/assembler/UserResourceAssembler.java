package no.gainmaster.user.web.rest.resource.assembler;

import no.gainmaster.user.service.User;
import no.gainmaster.user.web.rest.endpoint.UsersEndpoint;
import no.gainmaster.user.web.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UsersEndpoint.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource resource = createResourceWithId(user.getUserId(), user);
        //TODO: discover related services and add links (message-bus)
        resource.add(linkTo(methodOn(UsersEndpoint.class).getUser(user.getUsername())).slash("measurements")
            .withRel("measurements"));
        return resource;
    }

    @Override
    protected UserResource instantiateResource(User user) {
        UserResource resource = new UserResource();
        resource.setName(user.getName());
        resource.setUsername(user.getUsername());
        resource.setEmail(user.getEmail());
        return resource;
    }

}
