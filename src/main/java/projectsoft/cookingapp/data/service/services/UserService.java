package projectsoft.cookingapp.data.service.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.service.models.UserRegisterServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    User register(UserRegisterServiceModel userRegisterServiceModel);

    UserServiceModel findUserByUserName(String username);

    List<UserServiceModel> findAllUsers();
}
