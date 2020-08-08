package projectsoft.cookingapp.data.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectsoft.cookingapp.data.model.entity.Role;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel  extends BaseServiceModel{

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Set<RoleServiceModel> authorities;

}
