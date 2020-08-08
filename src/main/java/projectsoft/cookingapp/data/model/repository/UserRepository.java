package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);

    User findUserById(String id);
}
