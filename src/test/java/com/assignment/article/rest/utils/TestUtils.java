package com.assignment.article.rest.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.request.dto.UpdateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticlePage;
import com.assignment.article.rest.response.dto.ArticleResponseDTO;
import com.assignment.article.rest.response.dto.CreatedArticleResponseDTO;
import com.assignment.article.rest.response.dto.GetArticleResponseDTO;
import com.assignment.article.rest.response.dto.TagMetricesResponseDTO;
import com.assignment.article.rest.response.dto.TimeToReadArticleResponseDTO;
import com.assignment.article.rest.response.dto.UpdatedArticleResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");

    public static final String SAMPLE_SLUG_ID = "slugID";
    

    public static class DtoMappingException extends RuntimeException {

        public DtoMappingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

    public static CreateArticleRequestDTO buildCreateArticleRequestDTO() {
        CreateArticleRequestDTO dto = new CreateArticleRequestDTO();
        dto.setBody("Dummy Body");
        dto.setDescription("Dummy Description");
        Set<String> tags = new HashSet<>();
        tags.add("test1");
        tags.add("test2");
        dto.setTags(tags);
        dto.setTitle("Test");
        return dto;
    }
    
    public static GetArticleResponseDTO buildGetArticleRequestDTO() {
        GetArticleResponseDTO dto = new GetArticleResponseDTO();
        dto.setBody("Sample Body");
        dto.setDescription("Sample Description");
        Set<String> tags = new HashSet<>();
        tags.add("test1");
        tags.add("test2");
        dto.setTags(tags);
        dto.setTitle("Test");
        dto.setCreatedAt(DATE_FORMAT.format(new Date()));
        dto.setUpdatedAt(DATE_FORMAT.format(new Date()));
        return dto;
    }

    public static TimeToReadArticleResponseDTO buildTimeToReadArticleResponseDTO() {
        return new TimeToReadArticleResponseDTO("slug", 4, 50);
    }

    public static List<TagMetricesResponseDTO> buildResponseOfTagOccurences() {
        List<TagMetricesResponseDTO> response = new ArrayList<TagMetricesResponseDTO>();
        response.add(new TagMetricesResponseDTO("tag1", 4));
        response.add(new TagMetricesResponseDTO("tag2", 2));
        response.add(new TagMetricesResponseDTO("tag3", 2));
        return response;
    }
    
    public static UpdateArticleRequestDTO buildUpdateArticleRequestDTO() {
        UpdateArticleRequestDTO dto = new UpdateArticleRequestDTO();
        dto.setBody("Updated Body");
        dto.setDescription("Updated Description");
        dto.setTitle("Updated Title");
        return dto;
    }

    public static CreatedArticleResponseDTO buildCreateArticleResponseDTO(String slugId,
            CreateArticleRequestDTO requestDTO) {
        CreatedArticleResponseDTO dto = new CreatedArticleResponseDTO();
        dto.setSlug(slugId);
        dto.setBody(requestDTO.getBody());
        dto.setDescription(requestDTO.getDescription());
        dto.setTags(requestDTO.getTags());
        dto.setTitle(requestDTO.getTitle());
        return dto;
    }
    
    public static UpdatedArticleResponseDTO buildUpdateArticleResponseDTO(String slugId,
            UpdateArticleRequestDTO requestDTO) {
        UpdatedArticleResponseDTO dto = new UpdatedArticleResponseDTO();
        dto.setSlug(slugId);
        dto.setBody(requestDTO.getBody());
        dto.setDescription(requestDTO.getDescription());
        dto.setTitle(requestDTO.getTitle());
        dto.setCreatedAt(DATE_FORMAT.format(new Date()));
        dto.setUpdatedAt(DATE_FORMAT.format(new Date()));
        return dto;
    }
    
    public static ArticlePage buildArticlePage() {
        int pageNo = 0;
        boolean isLastPage = true;
        int toalPages = 1;
        List<ArticleResponseDTO> content = new ArrayList<>();
        content.add((ArticleResponseDTO) buildCreateArticleResponseDTO(SAMPLE_SLUG_ID, TestUtils.buildCreateArticleRequestDTO()));
        return new ArticlePage(pageNo, isLastPage, toalPages, content.size(), content);
    }

    public static <T> T getDtoFromMvcResult(final ObjectMapper objectMapper, final MvcResult result,
            final Class<T> classType) throws DtoMappingException {
        // Get the response and convert it into an object graph
        try {
            result.getResponse().setCharacterEncoding("UTF-8");
            String jsonString = result.getResponse().getContentAsString();
            if (StringUtils.hasText(jsonString)) {
                return objectMapper.readValue(jsonString, classType);
            }
        } catch (Exception exc) {
            throw new DtoMappingException("Problem mapping to DTO", exc);
        }
        return null;
    }

}
