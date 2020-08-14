package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.User;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate,String> {
    Rate findByIdAndUserContains(String id, User user);



    Rate findByRecipe_idContains(String id);

//    Rate findByPost(Post post);

    List<Rate> findAllByUser_IdContains(String id);
}
