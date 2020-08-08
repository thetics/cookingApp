package projectsoft.cookingapp.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import projectsoft.cookingapp.data.service.models.SavedRecipeServiceModel;
import projectsoft.cookingapp.data.service.services.RecipeService;
import projectsoft.cookingapp.data.service.services.SavedRecipeService;
import projectsoft.cookingapp.data.service.services.UserService;
import projectsoft.cookingapp.web.controllers.base.BaseController;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/saved")
public class SavedRecipeController extends BaseController {

    private final SavedRecipeService savedRecipeService;
    private final RecipeService recipeService;

    public SavedRecipeController(UserService userService, SavedRecipeService savedRecipeService, RecipeService recipeService) {
        super(userService);
        this.savedRecipeService = savedRecipeService;
        this.recipeService = recipeService;
    }

    @PostMapping("/save/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView saveRecipe(@PathVariable String id, Principal principal, @ModelAttribute("savedRecipeServiceModel")
            SavedRecipeServiceModel savedRecipeServiceModel, ModelAndView modelAndView) {

        var recipe = this.recipeService.findById(id);
        var user = getUsername(principal);
        savedRecipeServiceModel.setUser(user);
        savedRecipeServiceModel.setRecipe(recipe);

        this.savedRecipeService.saveRecipe(savedRecipeServiceModel);



        return super.redirect("/saved/all");

    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getAll(Principal principal, ModelAndView modelAndView) {
        var user = getUsername(principal);
        List<SavedRecipeServiceModel> all = this.savedRecipeService.findAll(user.getId());

        modelAndView.addObject("recipes", all);
        modelAndView.addObject("username", user.getUsername());

        modelAndView.setViewName("fragments/saved-recipesArea");
        modelAndView.setViewName("recipetemplates/recipes-saved");

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView delete(@PathVariable String id) {
        this.savedRecipeService.delete(id);

        return super.redirect("/saved/all");
    }


}
