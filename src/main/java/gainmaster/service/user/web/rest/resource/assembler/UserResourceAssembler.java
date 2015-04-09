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
        return resource;
    }

    @Override
    protected UserResource instantiateResource(UserEntity entity) {
        UserResource resource = new UserResource();
        resource.setUsername(entity.getUsername());
        return resource;
    }
}
