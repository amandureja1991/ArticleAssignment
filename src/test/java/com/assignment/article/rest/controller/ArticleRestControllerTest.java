package com.assignment.article.rest.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.article.exception.handler.ArticleExceptionHandler;
import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.request.dto.UpdateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticlePage;
import com.assignment.article.rest.response.dto.CreatedArticleResponseDTO;
import com.assignment.article.rest.response.dto.GetArticleResponseDTO;
import com.assignment.article.rest.response.dto.TimeToReadArticleResponseDTO;
import com.assignment.article.rest.response.dto.UpdatedArticleResponseDTO;
import com.assignment.article.rest.utils.TestUtils;
import com.assignment.article.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ObjectMapper.class, ArticleExceptionHandler.class
})
public class ArticleRestControllerTest {

    private static final String SAMPLE_SERVER_URL = "http://localhost:8080/article/";

    private MockMvc mockMvc;

    @InjectMocks
    private ArticleRestController restController;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private ArticleExceptionHandler exceptionHandler;

    @Mock
    private ArticleService mockArticleService;

    @Captor
    private ArgumentCaptor<CreateArticleRequestDTO> createdArticleCaptor;

    @Captor
    private ArgumentCaptor<UpdateArticleRequestDTO> updateArticleCaptor;

    @Captor
    private ArgumentCaptor<String> slugIdCaptor;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
    }

    @Test
    public void testSave() throws Exception {
        CreateArticleRequestDTO requestedJobDto = TestUtils.buildCreateArticleRequestDTO();

        CreatedArticleResponseDTO responseDTO = TestUtils.buildCreateArticleResponseDTO(TestUtils.SAMPLE_SLUG_ID,
                                                                                        requestedJobDto);
        final String contentBody = jsonMapper.writeValueAsString(requestedJobDto);
        Mockito.when(this.mockArticleService.save(Mockito.anyString(), Mockito.any(CreateArticleRequestDTO.class)))
                .thenReturn(responseDTO);

        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post(SAMPLE_SERVER_URL + TestUtils.SAMPLE_SLUG_ID).content(contentBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final CreatedArticleResponseDTO actualResponse = TestUtils.getDtoFromMvcResult(this.jsonMapper, mvcResult,
                                                                                       CreatedArticleResponseDTO.class);

        Mockito.verify(this.mockArticleService).save(slugIdCaptor.capture(), createdArticleCaptor.capture());
        Assert.assertEquals(requestedJobDto.getBody(), createdArticleCaptor.getValue().getBody());
        Assert.assertEquals(requestedJobDto.getDescription(), createdArticleCaptor.getValue().getDescription());
        Assert.assertEquals(requestedJobDto.getTitle(), createdArticleCaptor.getValue().getTitle());
        Assert.assertEquals(requestedJobDto.getTags(), createdArticleCaptor.getValue().getTags());
        Assert.assertEquals(responseDTO, actualResponse);
        Assert.assertEquals(TestUtils.SAMPLE_SLUG_ID, slugIdCaptor.getValue());
    }

    @Test
    public void testUpdate() throws Exception {
        UpdateArticleRequestDTO requestedJobDto = TestUtils.buildUpdateArticleRequestDTO();

        UpdatedArticleResponseDTO responseDTO = TestUtils.buildUpdateArticleResponseDTO(TestUtils.SAMPLE_SLUG_ID,
                                                                                        requestedJobDto);
        final String contentBody = jsonMapper.writeValueAsString(requestedJobDto);
        Mockito.when(this.mockArticleService.update(Mockito.anyString(), Mockito.any(UpdateArticleRequestDTO.class)))
                .thenReturn(responseDTO);

        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.put(SAMPLE_SERVER_URL + TestUtils.SAMPLE_SLUG_ID).content(contentBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final UpdatedArticleResponseDTO actualResponse = TestUtils.getDtoFromMvcResult(this.jsonMapper, mvcResult,
                                                                                       UpdatedArticleResponseDTO.class);

        Mockito.verify(this.mockArticleService).update(slugIdCaptor.capture(), updateArticleCaptor.capture());
        Assert.assertEquals(requestedJobDto.getBody(), updateArticleCaptor.getValue().getBody());
        Assert.assertEquals(requestedJobDto.getDescription(), updateArticleCaptor.getValue().getDescription());
        Assert.assertEquals(requestedJobDto.getTitle(), updateArticleCaptor.getValue().getTitle());
        Assert.assertEquals(responseDTO, actualResponse);
        Assert.assertEquals(TestUtils.SAMPLE_SLUG_ID, slugIdCaptor.getValue());
    }

    @Test
    public void testGet() throws Exception {
        GetArticleResponseDTO responseDTO = TestUtils.buildGetArticleRequestDTO();

        Mockito.when(this.mockArticleService.get(TestUtils.SAMPLE_SLUG_ID)).thenReturn(responseDTO);

        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get(SAMPLE_SERVER_URL + TestUtils.SAMPLE_SLUG_ID))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final GetArticleResponseDTO actualResponse = TestUtils.getDtoFromMvcResult(this.jsonMapper, mvcResult,
                                                                                   GetArticleResponseDTO.class);
        Mockito.verify(this.mockArticleService).get(slugIdCaptor.capture());
        Assert.assertEquals(responseDTO, actualResponse);
        Assert.assertEquals(TestUtils.SAMPLE_SLUG_ID, slugIdCaptor.getValue());
    }

    @Test
    public void testDelete() throws Exception {
        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.delete(SAMPLE_SERVER_URL + TestUtils.SAMPLE_SLUG_ID))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(this.mockArticleService).delete(slugIdCaptor.capture());
        Assert.assertEquals(TestUtils.SAMPLE_SLUG_ID, slugIdCaptor.getValue());
    }

    @Test
    public void testGetTimeToReadArticle() throws Exception {
        TimeToReadArticleResponseDTO responseDTO = TestUtils.buildTimeToReadArticleResponseDTO();

        Mockito.when(this.restController.getTimeToReadArticle(TestUtils.SAMPLE_SLUG_ID)).thenReturn(responseDTO);

        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get(SAMPLE_SERVER_URL + "time/" + TestUtils.SAMPLE_SLUG_ID))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final TimeToReadArticleResponseDTO actualResponse = TestUtils
                .getDtoFromMvcResult(this.jsonMapper, mvcResult, TimeToReadArticleResponseDTO.class);
        Mockito.verify(this.mockArticleService).getTimeToReadArticle(slugIdCaptor.capture());
        Assert.assertEquals(responseDTO.getArticleId(), actualResponse.getArticleId());
        Assert.assertEquals(responseDTO.getMins(), actualResponse.getMins());
        Assert.assertEquals(responseDTO.getSeconds(), actualResponse.getSeconds());
        Assert.assertEquals(TestUtils.SAMPLE_SLUG_ID, slugIdCaptor.getValue());
    }

    @Test
    public void testGetAllArticles() throws Exception {
        ArticlePage page = TestUtils.buildArticlePage();

        Mockito.when(this.mockArticleService.getArticles(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);

        this.mockMvc.perform(MockMvcRequestBuilders.get(SAMPLE_SERVER_URL))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
