package projectsoft.cookingapp.data.service.models;


import java.util.List;

public class RateServiceModel {

    private String id;
    private int count;
    private RecipeServiceModel recipe;
    private List<UserServiceModel> user;

    public RateServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public RecipeServiceModel getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeServiceModel recipe) {
        this.recipe = recipe;
    }

    public List<UserServiceModel> getUser() {
        return this.user;
    }

    public void setUser(List<UserServiceModel> user) {
        this.user = user;
    }
}
