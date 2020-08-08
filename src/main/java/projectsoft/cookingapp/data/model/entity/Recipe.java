package projectsoft.cookingapp.data.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {

    private String name;
    private String imageUrl;
    private String products;
    private String description;
    private Date postTime;
    private User uploader;
    private Rate rate;
    private Category category;

//    private List<Comment> comments;

    public Recipe() {
    }

    @Column(name = "recipe_name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //TODO MAYBE PROBLEM
    @ManyToOne(fetch = FetchType.EAGER)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "image")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "products", nullable = false, columnDefinition = "TEXT")
    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    @Column(name = "post_time")
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
    @OneToOne(cascade = CascadeType.ALL)
    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
