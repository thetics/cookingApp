package projectsoft.cookingapp.data.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rate")
public class Rate extends BaseEntity {

    private int count;
    private Recipe recipe;
    private List<User> user;

    public Rate() {
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @OneToOne(targetEntity = Recipe.class)
    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    @ManyToMany(targetEntity = User .class)
    @JoinTable(
            name = "users_rates",
            joinColumns = @JoinColumn(name = "rate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    public List<User> getUser() {
        return this.user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
