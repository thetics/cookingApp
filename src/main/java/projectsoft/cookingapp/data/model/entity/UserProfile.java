package projectsoft.cookingapp.data.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String imgUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "id"
    )
    private User user;


}
