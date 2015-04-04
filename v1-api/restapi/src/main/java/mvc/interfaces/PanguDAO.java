package mvc.interfaces;

import mvc.domain.PanguModel;

import java.util.List;

/**
 * Created by Mark on 01/04/15.
 */
public interface PanguDAO {
    public List<PanguModel> getAllModels();
    public PanguModel findModelById(int id);
    public PanguModel findModelByName(String panguName);
    public void updateModel(PanguModel panguModel);
    public void addModel(PanguModel panguModel);
    public void deleteModel(int id);
}
