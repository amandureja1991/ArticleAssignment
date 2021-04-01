package com.assignment.article.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignment.article.repository.TagRepository;
import com.assignment.article.rest.response.dto.TagMetricesResponseDTO;

@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceTest {

    @InjectMocks
    private TagService service;

    @Mock
    private TagRepository mockRepository;

    @Test
    public void testGetTagMetrix() {
        List<Object[]> tagMetrix = new ArrayList<Object[]>();
        Object[] obj1 = new Object[2];
        obj1[0] = "tag1";
        obj1[1] = 1;
        tagMetrix.add(obj1);

        Object[] obj2 = new Object[2];
        obj2[0] = "tag2";
        obj2[1] = 5;
        tagMetrix.add(obj2);

        Mockito.when(this.mockRepository.findTagOccurence()).thenReturn(tagMetrix);
        List<TagMetricesResponseDTO> metrix = this.service.getTagMetrix();

        for (int i = 0; i < metrix.size(); i++) {
            Assert.assertEquals(tagMetrix.get(i)[0], metrix.get(i).getTag());
            Assert.assertEquals(tagMetrix.get(i)[1], metrix.get(i).getOccurence());
        }
    }

}
