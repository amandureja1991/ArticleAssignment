package com.assignment.article.rest.response.dto;

import java.util.List;

public class ArticlePage {

    private int pageNumber;

    private boolean lastPage;

    private long totalPages;

    private long totalResults;

    private List<ArticleResponseDTO> content;

    public ArticlePage(int pageNumber, boolean lastPage, long totalPages, long totalResults,
            List<ArticleResponseDTO> content) {
        super();
        this.pageNumber = pageNumber;
        this.lastPage = lastPage;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<ArticleResponseDTO> content) {
        this.content = content;
    }

}
