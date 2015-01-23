package com.controller;

import com.domain.ErrorModel;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mark on 23/01/15.
 */
@RestController
public class ErrorControl implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ErrorModel error() {
        return new ErrorModel("500", "Server Error");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
