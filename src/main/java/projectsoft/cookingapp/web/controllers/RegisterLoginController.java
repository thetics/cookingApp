package projectsoft.cookingapp.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectsoft.cookingapp.data.service.models.UserRegisterServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.UserService;
import projectsoft.cookingapp.web.models.binding.UserRegisterBindingModel;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegisterLoginController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    public RegisterLoginController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String register( Model model) {
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel"
                    ,new UserRegisterBindingModel());
        }
        return "register";
    }


    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel"
                    ,bindingResult);

            return  "redirect:/users/register";

        }else {
            this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
            return  "redirect:/home";

        }



    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
