package projectsoft.cookingapp.service.implementations;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import projectsoft.cookingapp.base.TestBase;
import projectsoft.cookingapp.data.model.entity.SavedRecipe;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.model.repository.SavedRecipeRepository;
import projectsoft.cookingapp.data.service.models.SavedRecipeServiceModel;
import projectsoft.cookingapp.data.service.services.SavedRecipeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SavedRecipeImplTest extends TestBase {
    @MockBean
    SavedRecipeRepository savedRecipeRepository;

    @MockBean
    SavedRecipeService savedRecipeService;

    @Autowired
    SavedRecipeService service;

    @Test
    void getSavedRecipeForUser_whenNoSavedRecipe_shouldReturnEmptyList() {
        List<SavedRecipeServiceModel> savedRecipeServiceModels = service.findAll("1");
        assertEquals(0, savedRecipeServiceModels.size());
    }

    @Test
    void getSavedRecipeByUser_whenSavedRecipe_shouldReturnUserId() {
        SavedRecipe recipe = new SavedRecipe();
        recipe.setId("1");

        User user = new User();
        user.setId("2");

        recipe.setUser(user);
        this.savedRecipeRepository.saveAndFlush(recipe);

        assertEquals("2", recipe.getUser().getId());

    }
}