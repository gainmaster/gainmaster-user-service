package gainmaster.service.user.web.rest.resource.assembler;

import gainmaster.service.user.service.User;
import gainmaster.service.user.web.rest.endpoint.UsersEndpoint;
import gainmaster.service.user.web.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UsersEndpoint.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource resource = createResourceWithId(user.getUserId(), user);
        //TODO: discover related services and add links (message-bus)
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
