package projectsoft.cookingapp.data.service.services;

import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.CategoryName;

import java.util.List;

public interface CategoryService {

    void initCategories();

    List<Category> getAllCategories();

    Category getCategoryByName(CategoryName categoryName);

}
