package projectsoft.cookingapp.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectsoft.cookingapp.data.model.binding.RecipeAddBindingModel;
import projectsoft.cookingapp.data.model.binding.RecipeAddBindingModelForEdit;
import projectsoft.cookingapp.data.model.view.RecipeViewModel;
import projectsoft.cookingapp.data.service.models.RateServiceModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.CloudinaryService;
import projectsoft.cookingapp.data.service.services.RateService;
import projectsoft.cookingapp.data.service.services.RecipeService;
import projectsoft.cookingapp.data.service.services.UserService;
import projectsoft.cookingapp.validation.post.PostCreateValidator;
import projectsoft.cookingapp.web.controllers.base.BaseController;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/recipes")
public class RecipeController extends BaseController {
    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final UserService userService;
    private final RateService rateService;
    private final CloudinaryService cloudinaryService;
    private final PostCreateValidator postCreateValidator;


    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, UserService userService, RateService rateService, CloudinaryService cloudinaryService, PostCreateValidator postCreateValidator) {
        super(userService);
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.userService = userService;
        this.rateService = rateService;
        this.cloudinaryService = cloudinaryService;
        this.postCreateValidator = postCreateValidator;
    }


    @GetMapping("/all-recipes")
    public ModelAndView allrecipes(ModelAndView modelAndView) {

        modelAndView.addObject("recipes", this.recipeService.findAllItems());
        modelAndView.setViewName("fragments/recipesArea");
        modelAndView.setViewName("allrecipes");
        return modelAndView;
    }


    @GetMapping("/recipe-details/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView recipeDetails(Principal principal, @PathVariable String id, ModelAndView modelAndView) {

        var user = getUsername(principal);
        RecipeViewModel recipeViewModel = this.modelMapper.map(this.recipeService.findById(id),RecipeViewModel.class);

        modelAndView.addObject("recipe", recipeViewModel);
        modelAndView.addObject("recipeID", id);
        modelAndView.addObject("username", user.getUsername());

//        RateServiceModel rateByPostId = this.rateService.findRateByPostId(id);
//        boolean isVoted = this.rateService.userHasRated(rateByPostId.getId(), user.getId());

        modelAndView.setViewName("recipe-details");
        return modelAndView;
    }


    @GetMapping("/recipe-post")
    @PreAuthorize("hasAuthority('USER')")
    public String recipePost(Model model) {
        if (!model.containsAttribute("recipeAddBindingModel")) {
            model.addAttribute("recipeAddBindingModel", new RecipeAddBindingModel());
        }
        return "recipe-post";
    }


    @PostMapping("/recipe-post")
    @PreAuthorize("hasAuthority('USER')")
    public String recipePostConfirm(@ModelAttribute("recipeAddBindingModel")
                                            RecipeAddBindingModel recipeAddBindingModel,
                                    BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) throws IOException {

        this.postCreateValidator.validate(recipeAddBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel", bindingResult);

            return "redirect:/recipes/recipe-post";
        } else {
            RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class);
            UserServiceModel user = this.userService.findUserByUserName(principal.getName());
            RateServiceModel rateServiceModel = new RateServiceModel();

            recipeServiceModel.setPostTime(new Date());
            recipeServiceModel.setImageUrl(this.cloudinaryService.uploadImage(recipeAddBindingModel.getImage()));
            recipeServiceModel.setUploader(user);


            this.recipeService.createPost(recipeServiceModel);
            this.rateService.create(rateServiceModel, recipeServiceModel);

//            this.recipeService.addItem(
//                    this.modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class));


            return "redirect:/home";
        }

    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
public String deletePost(Principal principal, @PathVariable String id, ModelAndView modelAndView){

        this.recipeService.deleteOneById(id);

        return  "redirect:/recipes/all-recipes";
    }


    @GetMapping("/recipe-edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView editPost(@PathVariable String id, @ModelAttribute("recipeAddBindingModel") RecipeAddBindingModel recipeAddBindingModel,
                          ModelAndView modelAndView,
                           Principal principal){


        RecipeServiceModel recipeServiceModel = this.recipeService.findById(id);



        modelAndView.addObject("recipe", recipeServiceModel);
        modelAndView.addObject("recipeId", id);
        modelAndView.addObject("username", principal.getName());


        modelAndView.setViewName("recipetemplates/recipe-edit");


        return modelAndView;
    }

    @PostMapping("/recipe-edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public  ModelAndView editPostConfirm(@PathVariable String id, @ModelAttribute("recipeAddBindingModelForEdit")
            RecipeAddBindingModelForEdit recipeAddBindingModelForEdit) throws IOException {


        if (recipeAddBindingModelForEdit.getDescription().length() < 10 || recipeAddBindingModelForEdit.getProducts().length() < 5 ||
                recipeAddBindingModelForEdit.getName().length() < 2) {
            return super.redirect("/recipes/recipe-edit/" + id);
        }

        RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeAddBindingModelForEdit, RecipeServiceModel.class);

        if (recipeAddBindingModelForEdit.getImageUrl().getBytes().length != 0) {
            recipeServiceModel.setImageUrl(this.cloudinaryService.uploadImage(recipeAddBindingModelForEdit.getImageUrl()));
        }


        this.recipeService.editRecipe(id, recipeServiceModel);

        return super.redirect("/recipes/recipe-details/" + id);
    }
}
