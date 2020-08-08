package projectsoft.cookingapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import projectsoft.cookingapp.data.service.services.CategoryService;
import projectsoft.cookingapp.data.service.services.RecipeService;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final CategoryService categoryService;
    private final RecipeService recipeService;

    public HomeController(CategoryService categoryService, RecipeService recipeService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {


        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, HttpSession httpSession) {

//        httpSession.setAttribute("cats", this.categoryService.getAllCategories());

        modelAndView.addObject("cats", this.categoryService.getAllCategories());
        modelAndView.addObject("recipes", this.recipeService.findAllItems());

        modelAndView.setViewName("fragments/navbar");
        modelAndView.setViewName("fragments/heroArea");
        modelAndView.setViewName("fragments/bestrecipesArea");
        modelAndView.setViewName("home");
        return modelAndView;

    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
