package projectsoft.cookingapp.data.service.services;

import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.Role;
import projectsoft.cookingapp.data.model.repository.RoleRepository;
import projectsoft.cookingapp.data.service.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void seedRolesInDb() {
this.roleRepository.saveAndFlush(new Role("ADMIN"));
this.roleRepository.saveAndFlush(new Role("USER"));
    }
}
