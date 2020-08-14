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


public class RecipeAddBindingModel {


    private String name;
    private MultipartFile image;
    private String products;
    private String description;
    private Date postTime;
    private CategoryName category;

    public RecipeAddBindingModel() {
    }

    @NotEmpty(message = "Recipe name cannot be empty")
    @Size(min = 2, max = 30, message = "Recipe should be between 2 and 30")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Image cannot be empty")
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @NotEmpty(message = "Products cannot be empty")
    @Size(min = 10, message = "Recipe should be min 10")
    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, message = "Description should be min 10")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }
}
