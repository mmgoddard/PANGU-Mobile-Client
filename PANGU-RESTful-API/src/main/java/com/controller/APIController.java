package com.controller;

import com.domain.PanguModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.service.APIRepository;
import java.util.List;

/**
 * Created by Mark on 23/01/15.
 */
@RequestMapping("/api/**")
@RestController
public class APIController {
    @Autowired
    APIRepository apiRepository;

    /**
     * Returns a list of PANGU records.
     * @return list
     */
    @RequestMapping(value="/models", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<PanguModel> findAll() {
        return apiRepository.findAll();
    }

    /**
     * Returns a record based on the id given.
     * @param panguId
     * @return
     */
    @RequestMapping(value="/models/{panguId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public PanguModel findById(@PathVariable("panguId") long panguId) {
        return apiRepository.findById(panguId);
    }

    /**
     * Adds a new record to the database.
     * Updates a existing record in the database.
     * @param panguModel
     * @return
     */
    @RequestMapping(value="/models/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addRecord(@RequestBody PanguModel panguModel) {
        apiRepository.save(panguModel);
    }

    /**
     * Deletes an existing record from the database.
     * @param panguId
     * @return
     */
    @RequestMapping(value="/models/{panguId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteRecord(@PathVariable long panguId) {
        apiRepository.delete(panguId);
    }
}