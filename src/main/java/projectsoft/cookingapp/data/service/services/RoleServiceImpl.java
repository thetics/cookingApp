package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.Role;
import projectsoft.cookingapp.data.model.repository.RoleRepository;
import projectsoft.cookingapp.data.service.models.RoleServiceModel;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;



    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedRolesInDb() {
this.roleRepository.saveAndFlush(new Role("ADMIN"));
this.roleRepository.saveAndFlush(new Role("USER"));
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);

    }
}
