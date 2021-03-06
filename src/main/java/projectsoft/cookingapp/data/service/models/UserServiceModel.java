package projectsoft.cookingapp.data.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectsoft.cookingapp.data.model.entity.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserServiceModel  extends BaseServiceModel{

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
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
    @NotEmpty(message = "Email cannot be empty")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
