package com.assignment.article.rest.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.assignment.article.rest.response.dto.TagMetricesResponseDTO;
import com.assignment.article.rest.utils.TestUtils;
import com.assignment.article.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ObjectMapper.class
})
public class TagRestControllerTest {

    private static final String SAMPLE_SERVER_URL = "http://localhost:8080/article/tag";

    private MockMvc mockMvc;

    @InjectMocks
    private TagRestController restController;

    @Autowired
    private ObjectMapper jsonMapper;

    @Mock
    private TagService mockTagService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
    }

    @Test
    public void testGetOccurrences() throws Exception {
        List<TagMetricesResponseDTO> responseDTO = TestUtils.buildResponseOfTagOccurences();

        Mockito.when(this.restController.getOccurrences()).thenReturn(responseDTO);

        final MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get(SAMPLE_SERVER_URL + "/metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final List<TagMetricesResponseDTO> actualResponse = TestUtils.getDtoFromMvcResult(this.jsonMapper, mvcResult,
                                                                                          List.class);
        Mockito.verify(this.mockTagService).getTagMetrix();
        Assert.assertNotNull(actualResponse);
    }

}
