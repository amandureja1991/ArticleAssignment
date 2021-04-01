package com.assignment.article.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.article.rest.response.dto.TagMetricesResponseDTO;
import com.assignment.article.service.TagService;

@RestController
@RequestMapping("article/tag")
public class TagRestController {

    @Autowired
    private TagService service;

    @GetMapping("/metrics")
    public List<TagMetricesResponseDTO> getOccurrences() {
        return this.service.getTagMetrix();
    }

}
