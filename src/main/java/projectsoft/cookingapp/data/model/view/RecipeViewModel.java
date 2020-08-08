package projectsoft.cookingapp.data.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectsoft.cookingapp.data.model.entity.CategoryName;
import projectsoft.cookingapp.data.service.models.RateServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;

import java.util.Date;

@Getter @Setter@NoArgsConstructor
public class RecipeViewModel {

    private String id;
    private String name;
    private String imageUrl;
    private String products;
    private String description;
    private Date postTime;
    private UserServiceModel uploader;
    private RateServiceModel rate;
    private CategoryName categoryName;
}
