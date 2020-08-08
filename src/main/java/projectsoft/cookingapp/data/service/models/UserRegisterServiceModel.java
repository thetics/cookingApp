package projectsoft.cookingapp.data.service.models;

import projectsoft.cookingapp.data.model.entity.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserRegisterServiceModel {

    private String username;
    private String password;
    private String email;
    private Set<RoleServiceModel> authorities;

    public UserRegisterServiceModel() {
    }

    @NotEmpty(message = "Email cannot be empty")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }

    @NotEmpty(message = "Username name cannot be empty")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Password cannot be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
