package mvc.controllers;

import mvc.domain.ErrorModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Mark on 02/04/15.
 */
@Controller
@RequestMapping("/api/**")
public class ExceptionHandlingController {

    @ExceptionHandler({Exception.class})
    public @ResponseBody ErrorModel databaseError() {
        ErrorModel response = new ErrorModel("500", "The query resulted in a empty response.");
        return response;
    }
}
