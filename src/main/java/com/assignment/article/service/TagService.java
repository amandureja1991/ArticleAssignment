package com.assignment.article.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.article.repository.TagRepository;
import com.assignment.article.rest.response.dto.TagMetricesResponseDTO;

@Service
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagRepository repository;

    public List<TagMetricesResponseDTO> getTagMetrix() {
        List<Object[]> repoResponse = this.repository.findTagOccurence();
        List<TagMetricesResponseDTO> response = new ArrayList<>();
        for (Object[] obj : repoResponse) {
            response.add(new TagMetricesResponseDTO(obj[0].toString(), Integer.parseInt(obj[1].toString())));
        }
        return response;
    }

}
