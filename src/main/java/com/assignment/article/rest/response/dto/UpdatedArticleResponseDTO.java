package com.assignment.article.rest.response.dto;

public class UpdatedArticleResponseDTO extends ArticleResponseDTO {

    private String createdAt;

    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UpdatedArticleResponseDTO() {
        super();
    }

    public UpdatedArticleResponseDTO(ArticleResponseDTO baseResponse, String updatedAt, String createdAt) {
        this.slug = baseResponse.getSlug();
        this.body = baseResponse.getBody();
        this.title = baseResponse.getTitle();
        this.description = baseResponse.getDescription();
        this.tags = baseResponse.getTags();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UpdatedArticleResponseDTO other = (UpdatedArticleResponseDTO) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

}
