package gainmaster.service.user.web.rest.resource.assembler;

import gainmaster.service.user.entity.UserEntity;
import gainmaster.service.user.web.rest.endpoint.UsersEndpoint;
import gainmaster.service.user.web.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserEntity, UserResource> {

    public UserResourceAssembler() {
        super(UsersEndpoint.class, UserResource.class);
    }

    @Override
    public UserResource toResource(UserEntity entity) {
        UserResource resource = createResourceWithId(entity.getId(), entity);
        //TODO: discover related services and add links (message-bus)
        return resource;
    }

    @Override
    protected UserResource instantiateResource(UserEntity entity) {
        UserResource resource = new UserResource();
        resource.setName(entity.getName());
        resource.setUsername(entity.getUsername());
        resource.setEmail(entity.getEmail());
        return resource;
    }

}
