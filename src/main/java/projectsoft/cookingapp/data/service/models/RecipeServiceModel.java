package projectsoft.cookingapp.data.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.User;

import java.time.LocalDate;
import java.util.Date;



public class RecipeServiceModel extends BaseServiceModel{
    private String name;
    private String imageUrl;
    private String products;
    private String description;
    private Date postTime;
    private UserServiceModel uploader;
    private RateServiceModel rate;
    private Category category;

    public RecipeServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

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

    public UserServiceModel getUploader() {
        return uploader;
    }

    public void setUploader(UserServiceModel uploader) {
        this.uploader = uploader;
    }

    public RateServiceModel getRate() {
        return rate;
    }

    public void setRate(RateServiceModel rate) {
        this.rate = rate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
