package com.controller;

import com.domain.PanguModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.service.APIRepository;

import java.util.List;

/**
 * Created by Mark on 23/01/15.
 */
@RequestMapping("/api/**")
@RestController
public class APIController {
    private List<PanguModel> list;

    @Autowired
    APIRepository apiRepository;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<PanguModel> index() {
        return list = apiRepository.findAll();
    }
}