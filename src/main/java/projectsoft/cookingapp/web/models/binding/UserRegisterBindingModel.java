package projectsoft.cookingapp.web.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;


    public UserRegisterBindingModel() {
    }

    @NotEmpty(message = "Email cannot be empty")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @NotEmpty(message = "Password cannot be empty")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
