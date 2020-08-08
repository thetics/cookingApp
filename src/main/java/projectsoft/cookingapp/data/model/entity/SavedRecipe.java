package projectsoft.cookingapp.data.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "saved_recipes")
public class SavedRecipe extends BaseEntity {




    private User user;
    private Recipe recipe;


    public SavedRecipe() {
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @OneToOne(targetEntity = Recipe.class)
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
