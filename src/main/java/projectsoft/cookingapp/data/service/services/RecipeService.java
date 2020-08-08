package projectsoft.cookingapp.data.service.services;

import projectsoft.cookingapp.data.model.view.RecipeViewModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;

import java.util.List;

public interface RecipeService {

 RecipeServiceModel addItem(RecipeServiceModel recipeServiceModel);

    RecipeServiceModel createPost(RecipeServiceModel recipeServiceModel);

    List<RecipeViewModel> findAllItems();

    RecipeServiceModel findById(String id);

    void deleteOneById(String id);

    void deleteAll();

    RecipeServiceModel editRecipe(String id, RecipeServiceModel recipeServiceModel);

    List<RecipeServiceModel> getAllUserRecipes(String s);
}
