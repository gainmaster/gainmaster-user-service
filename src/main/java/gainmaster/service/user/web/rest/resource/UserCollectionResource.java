package gainmaster.service.user.web.rest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.Resources;

import java.util.List;

public class UserCollectionResource extends Resources<UserResource> {

    @JsonProperty
    private Integer pageSize;

    @JsonProperty
    private Integer pageNumber;

    @JsonProperty
    private Integer numberOfElementsOnPage;

    @JsonProperty
    private Integer totalNumberOfPages;

    @JsonProperty
    private Long totalNumberOfElements;


    @JsonCreator
    public UserCollectionResource(List<UserResource> userResources) {
        super(userResources);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getNumberOfElementsOnPage() {
        return numberOfElementsOnPage;
    }

    public void setNumberOfElementsOnPage(Integer numberOfElementsOnPage) {
        this.numberOfElementsOnPage = numberOfElementsOnPage;
    }

    public Integer getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(Integer totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public Long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public void setTotalNumberOfElements(Long totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }
}
