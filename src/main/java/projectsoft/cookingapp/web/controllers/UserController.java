package projectsoft.cookingapp.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import projectsoft.cookingapp.data.model.binding.UserEditBindingModel;
import projectsoft.cookingapp.data.model.view.UserAllViewModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.CloudinaryService;
import projectsoft.cookingapp.data.service.services.RecipeService;
import projectsoft.cookingapp.data.service.services.UserProfileService;
import projectsoft.cookingapp.data.service.services.UserService;
import projectsoft.cookingapp.errors.UserNotFoundException;
import projectsoft.cookingapp.web.controllers.base.BaseController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserProfileService userProfileService;
    private final RecipeService recipeService;
    private final CloudinaryService cloudinaryService;

    public UserController(UserService userService, ModelMapper modelMapper, UserProfileService userProfileService, RecipeService recipeService, CloudinaryService cloudinaryService) {
        super(userService);
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userProfileService = userProfileService;
        this.recipeService = recipeService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userProfile(Principal principal, ModelAndView modelAndView) {

        var user = getUsername(principal);

        var userProfile = this.userProfileService.findByUserId(user.getId());
        List<RecipeServiceModel> userRecipes = this.recipeService.getAllUserRecipes(user.getId());

        modelAndView.addObject("userRecipes", userRecipes);
        modelAndView.addObject("userProfile", userProfile);
        modelAndView.addObject("userID", user.getId());
        modelAndView.addObject("username", user.getUsername());
//        modelAndView.addObject("profileImage", userProfile.getImgUrl());
        modelAndView.setViewName("users/profile-recipes");
        modelAndView.setViewName("users/profile");
        return modelAndView;
    }


    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        var user = getUsername(principal);
        UserServiceModel userServiceModel = this.modelMapper.map(this.userProfileService.findByUserId(user.getId()), UserServiceModel.class);
        modelAndView.addObject("user", userServiceModel);
        modelAndView.addObject("username", user.getUsername());
        modelAndView.setViewName("users/edit");
        return modelAndView;

    }


    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(Principal principal, @ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel,
                                           BindingResult bindingResult) throws IOException {

        UserServiceModel userServiceModel = this.modelMapper.map(userEditBindingModel, UserServiceModel.class);
        userServiceModel.setUsername(principal.getName());

        String pas = userEditBindingModel.getPassword();
        System.out.println();
        if (userEditBindingModel.getPassword() != null && userEditBindingModel.getConfirmPassword() != null
                && userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            userServiceModel.setPassword(userEditBindingModel.getPassword());
        }

        if (!userEditBindingModel.getImage().isEmpty()) {
            userServiceModel.setImageUrl(this.cloudinaryService.uploadImage(userEditBindingModel.getImage()));
        }

        this.userService.editUserProfile(userServiceModel);

        return super.redirect("/users/profile");
    }

    @PostMapping("/delete/recipe/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(Principal principal, @PathVariable String id, ModelAndView modelAndView) {

        this.recipeService.deleteOneById(id);

        return "redirect:/users/profile";
    }


    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");

        return super.redirect("/users/all-users");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");

        return super.redirect("/users/all-users");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect("/users/all-users");
    }

    @GetMapping("/all-users")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getAllUser(ModelAndView modelAndView, Principal principal) {
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);
        modelAndView.addObject("username", principal.getName());
        modelAndView.setViewName("users/allusersfragment");
        modelAndView.setViewName("users/all-users");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView deleteMessage(@PathVariable String id) {
        this.userService.deleteUser(id);

        return super.redirect("/users/all-users");
    }


    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleException(UserNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

}
