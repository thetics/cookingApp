package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.SavedRecipe;

import java.util.List;

@Repository
public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, String> {

    List<SavedRecipe> findByUser_IdContains(String id);

    List<SavedRecipe> findAllByRecipe_IdContains(String id);

}
