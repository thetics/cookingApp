package projectsoft.cookingapp.data.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {

    private String firstName;
    private String lastName;
    private String imgUrl;
    private User user;

    public UserProfile() {
    }



    @Column
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "id"
    )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
