package projectsoft.cookingapp.data.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavedRecipeServiceModel extends BaseServiceModel {
    private UserServiceModel user;
    private RecipeServiceModel recipe;
}
