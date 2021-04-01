package com.assignment.article.rest.response.dto;

import org.apache.commons.lang3.StringUtils;

public class GetArticleResponseDTO extends ArticleResponseDTO {

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

    public GetArticleResponseDTO() {
        super();
    }

    public GetArticleResponseDTO(ArticleResponseDTO baseResponse, String updatedAt, String createdAt) {
        this.slug = baseResponse.getSlug();
        this.body = baseResponse.getBody();
        this.title = baseResponse.getTitle();
        this.description = baseResponse.getDescription();
        this.tags = baseResponse.getTags();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt == null ? StringUtils.EMPTY : updatedAt;
    }

}
