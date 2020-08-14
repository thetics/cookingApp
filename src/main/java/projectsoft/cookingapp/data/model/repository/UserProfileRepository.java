package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
    UserProfile findByUserId(String id);


}
