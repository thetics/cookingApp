package projectsoft.cookingapp.data.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectsoft.cookingapp.data.model.entity.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

 Role findByAuthority(String authority);
}
