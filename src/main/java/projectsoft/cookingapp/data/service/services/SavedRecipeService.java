package projectsoft.cookingapp.data.service.services;

import projectsoft.cookingapp.data.service.models.SavedRecipeServiceModel;

import java.util.List;

public interface SavedRecipeService  {
    void saveRecipe(SavedRecipeServiceModel savedRecipeServiceModel);

    void delete(String id);

    List<SavedRecipeServiceModel> findAll(String id);

}
