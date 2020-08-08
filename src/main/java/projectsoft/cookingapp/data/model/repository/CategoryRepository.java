package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.CategoryName;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> getCategoryByCategoryName(CategoryName categoryName);
}
