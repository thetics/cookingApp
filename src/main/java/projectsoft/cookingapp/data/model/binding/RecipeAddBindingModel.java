package projectsoft.cookingapp.data.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.CategoryName;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor

public class RecipeAddBindingModel {

    @NotEmpty(message = "Recipe name cannot be empty")
    @Size(min = 2, max = 30, message = "Recipe should be between 2 and 30")
    private String name;


    @NotNull(message = "Image cannot be empty")
    private MultipartFile image;


    @NotEmpty(message = "Products cannot be empty")
    @Size(min = 10, message = "Recipe should be min 10")
    private String products;


    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, message = "Description should be min 10")
    private String description;
    private Date postTime;
    private CategoryName category;



}
