package projectsoft.cookingapp.data.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.User;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class RecipeServiceModel extends BaseServiceModel{
    private String name;
    private String imageUrl;
    private String products;
    private String description;
    private Date postTime;
    private UserServiceModel uploader;
    private RateServiceModel rate;
    private Category category;
}
