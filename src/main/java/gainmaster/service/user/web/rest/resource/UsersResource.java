package gainmaster.service.user.web.rest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.Resources;

import java.util.List;

public class UsersResource extends Resources<UserResource> {

    @JsonProperty
    private Integer pageElements;

    @JsonProperty
    private Long totalElements;

    @JsonProperty
    private Integer totalPages;

    @JsonProperty
    private Integer pageSize;

    @JsonProperty
    private Integer pageNumber;

    @JsonCreator
    public UsersResource(List<UserResource> userResources) {
        super(userResources);
    }

    public void setPageElements(Integer pageElements) {
        this.pageElements = pageElements;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
