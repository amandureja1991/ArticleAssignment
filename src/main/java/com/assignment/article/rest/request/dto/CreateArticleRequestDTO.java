package com.assignment.article.rest.request.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class CreateArticleRequestDTO {

    @NotNull(message = "title can not be null")
    @NotEmpty(message = "title can not be empty")
    private String title;

    @NotNull(message = "description can not be null")
    @NotEmpty(message = "description can not be empty")
    private String description;

    @NotNull(message = "body can not be null")
    @NotEmpty(message = "body can not be empty")
    private String body;

    private Set<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getTags() {
        return tags.stream().map(String::toLowerCase).collect(Collectors.toSet());
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
