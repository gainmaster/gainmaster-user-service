package gainmaster.service.user.web.rest.resource.assembler;

import gainmaster.service.user.web.rest.resource.UserResource;
import gainmaster.service.user.entity.UserEntity;
import gainmaster.service.user.web.rest.endpoint.UsersEndpoint;
import gainmaster.service.user.web.rest.resource.UsersResource;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UsersResourceAssembler extends ResourceAssemblerSupport<Page<UserEntity>, UsersResource> {

    @Inject
    private UserResourceAssembler userResourceAssembler;

    @Inject
    private EntityLinks links;


    public UsersResourceAssembler() {
        super(UsersEndpoint.class, UsersResource.class);
    }


    @Override
    public UsersResource toResource(Page<UserEntity> page) {
        UsersResource usersResource = instantiateResource(page);
        //TODO: Make self rel a template
        usersResource.add(links.linkToCollectionResource(UserResource.class).withSelfRel());

        if (!page.isFirst()) {
            usersResource.add(linkTo(methodOn(UsersEndpoint.class).getUsers(
                String.valueOf(page.getSize()),
                String.valueOf(1)
            )).withRel("first"));
        }

        if (page.hasPrevious()) {
            usersResource.add(linkTo(methodOn(UsersEndpoint.class).getUsers(
                String.valueOf(page.getSize()),
                String.valueOf(page.getNumber())
            )).withRel("previous"));
        }

        usersResource.add(linkTo(methodOn(UsersEndpoint.class).getUsers(
            String.valueOf(page.getSize()),
            String.valueOf(page.getNumber() + 1)
        )).withRel("current"));

        if (page.hasNext()) {
            usersResource.add(linkTo(methodOn(UsersEndpoint.class).getUsers(
                String.valueOf(page.getSize()),
                String.valueOf(page.getNumber() + 2)
            )).withRel("next"));
        }

        if (!page.isLast()) {
            usersResource.add(linkTo(methodOn(UsersEndpoint.class).getUsers(
                String.valueOf(page.getSize()),
                String.valueOf(page.getTotalPages())
            )).withRel("last"));
        }
        return usersResource;
    }

    @Override
    protected UsersResource instantiateResource(Page<UserEntity> page) {
        List<UserResource> userResources = userResourceAssembler.toResources(page);

        UsersResource usersResource = new UsersResource(userResources);
        usersResource.setPageElements(page.getNumberOfElements());
        usersResource.setPageSize(page.getSize());
        usersResource.setPageNumber(page.getNumber() + 1);
        usersResource.setTotalElements(page.getTotalElements());
        usersResource.setTotalPages(page.getTotalPages());

        return usersResource;
    }
}
