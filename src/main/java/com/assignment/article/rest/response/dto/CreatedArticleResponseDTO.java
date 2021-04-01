package com.assignment.article.rest.response.dto;

public class CreatedArticleResponseDTO extends ArticleResponseDTO {

    public CreatedArticleResponseDTO() {
        super();
    }

    public CreatedArticleResponseDTO(ArticleResponseDTO baseResponse) {
        this.slug = baseResponse.getSlug();
        this.body = baseResponse.getBody();
        this.title = baseResponse.getTitle();
        this.description = baseResponse.getDescription();
        this.tags = baseResponse.getTags();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }

}
