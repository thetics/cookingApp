package projectsoft.cookingapp.data.service.models;

import projectsoft.cookingapp.data.model.entity.User;

public class UserProfileServiceModel extends BaseServiceModel {

    private String firstName;
    private String lastName;
    private String imageUrl;
    private User user;

    public UserProfileServiceModel() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
