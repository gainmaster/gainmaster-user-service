package gainmaster.service.user.web.rest.resource.assembler;

import gainmaster.service.user.service.User;
import gainmaster.service.user.web.rest.resource.UserResource;
import gainmaster.service.user.web.rest.endpoint.UsersEndpoint;
import gainmaster.service.user.web.rest.resource.UserCollectionResource;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class UserCollectionResourceAssembler extends ResourceAssemblerSupport<Page<User>, UserCollectionResource> {

    @Inject
    private UserResourceAssembler userResourceAssembler;

    @Inject
    private EntityLinks links;


    public UserCollectionResourceAssembler() {
        super(UsersEndpoint.class, UserCollectionResource.class);
    }


    @Override
    public UserCollectionResource toResource(Page<User> page) {
        UserCollectionResource resource = instantiateResource(page);


        UriTemplate uriTemplate = new UriTemplate(
            links.linkToCollectionResource(UserResource.class).getHref(),
            new TemplateVariables(
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM)
            )
        );

        resource.add(new Link(uriTemplate, "self"));

        if (!page.isFirst()) {
            resource.add(new Link(uriTemplate.expand(page.getSize(), 1).toString(), "first"));
        }

        if (page.hasPrevious()) {
            resource.add(new Link(uriTemplate.expand(page.getSize(), page.getNumber()).toString(), "previous"));
        }

        resource.add(new Link(uriTemplate.expand(page.getSize(), page.getNumber() + 1).toString(), "current"));

        if (page.hasNext()) {
            resource.add(new Link(uriTemplate.expand(page.getSize(), page.getNumber() + 2).toString(), "next"));
        }

        if (!page.isLast()) {
            resource.add(new Link(uriTemplate.expand(page.getSize(), page.getTotalPages()).toString(), "last"));
        }

        return resource;
    }

    @Override
    protected UserCollectionResource instantiateResource(Page<User> page) {
        List<UserResource> embeddedResources = userResourceAssembler.toResources(page);

        UserCollectionResource resource = new UserCollectionResource(embeddedResources);
        resource.setPageNumber(page.getNumber() + 1);
        resource.setPageSize(page.getSize());
        resource.setNumberOfElementsOnPage(page.getNumberOfElements());
        resource.setTotalNumberOfElements(page.getTotalElements());
        resource.setTotalNumberOfPages(page.getTotalPages());

        return resource;
    }
}
