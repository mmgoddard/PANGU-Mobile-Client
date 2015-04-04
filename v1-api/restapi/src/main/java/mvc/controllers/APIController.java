package mvc.controllers;

import mvc.domain.PanguModel;
import mvc.service.PanguDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/**")
public class APIController {
    @Autowired
    PanguDAOImpl panguDAO;

	@RequestMapping(value="/models", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<PanguModel> findAll() {
        return panguDAO.getAllModels();
    }

    @RequestMapping(value="/models/{panguId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PanguModel findById(@PathVariable("panguId") int panguId) {
        return panguDAO.findModelById(panguId);
    }

    @RequestMapping(value="models/name/{panguName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PanguModel findByName(@PathVariable("panguName") String panguName) {
        return panguDAO.findModelByName(panguName);
    }

    @RequestMapping(value="/models/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody void addRecord(@RequestBody PanguModel panguModel) {
        panguDAO.addModel(panguModel);
    }

    @RequestMapping(value="/models/update", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody void updateRecord(@RequestBody PanguModel panguModel) {
        panguDAO.updateModel(panguModel);
    }

    @RequestMapping(value="/models/{panguId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody void deleteRecord(@PathVariable int panguId) {
        panguDAO.deleteModel(panguId);
    }
}