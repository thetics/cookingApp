package projectsoft.cookingapp.data.service.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.service.models.UserRegisterServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    User register(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);

    void editUserProfile(UserServiceModel userServiceModel);

    void deleteUser(String id);
}
