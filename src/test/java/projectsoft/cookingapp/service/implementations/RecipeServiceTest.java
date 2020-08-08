package projectsoft.cookingapp.service.implementations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import projectsoft.cookingapp.base.TestBase;
import projectsoft.cookingapp.data.model.entity.Recipe;
import projectsoft.cookingapp.data.model.repository.RecipeRepository;
import projectsoft.cookingapp.data.model.view.RecipeViewModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RecipeServiceTest extends TestBase {

    @MockBean
    RecipeRepository recipeRepository;

    @MockBean
    RecipeService recipeService;

    @Autowired
    RecipeService service;

    @Test
    void getPostForUser_whenNoPost_shouldReturnEmptyList() {
        List<RecipeServiceModel> recipeServiceModels = service.getAllUserRecipes("1");
        assertEquals(0, recipeServiceModels.size());
    }

    @Test
    void getPostForUser_whenPost_shouldReturnUploader() {
        UserServiceModel user = new UserServiceModel();
        user.setId("1");

        RecipeServiceModel post = new RecipeServiceModel();
        post.setUploader(user);
        service.createPost(post);

        assertEquals("1", post.getUploader().getId());

    }
//    @Test
//    public void findById_whenIdIsCorrect_shouldReturnRecipe() {
//
//        Recipe recipe = new Recipe();
//        recipe.setId("1");
//
//        when(recipeRepository.findRecipeById("1"))
//                .thenReturn(recipe);
//
//        RecipeServiceModel recipeServiceModel = recipeService.findById("1");
//        System.out.println();
//        assertEquals("1", recipeServiceModel.getId());
//    }

    @Test
    public void getAll_whenThereAreNoRecipes_shouldReturnEmpty() {
        when(recipeRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<RecipeViewModel> recipeViewModels = recipeService.findAllItems();

        assertEquals(0, recipeViewModels.size());
    }
//    @Test
//    public void getAll_whenThereAreProducts_shouldReturnAllProducts() {
//        List<Recipe> recipes = new ArrayList<>();
//        recipes.add(new Recipe());
//        recipes.add(new Recipe() );
//
//
//        when(recipeRepository.findAll())
//                .thenReturn(recipes);
//
//        List<RecipeViewModel> recipeViewModels = recipeService.findAllItems();
//
//        assertEquals(2, recipeViewModels.size());
//    }
}