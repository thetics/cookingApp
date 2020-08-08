package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {

    Optional<Recipe> findByName(String name);
    Recipe findByImageUrl(String imageUrl);
    List<Recipe> findByUploader_IdContainsOrderByPostTimeDesc(String id);

    Recipe findRecipeById(String id);

}
