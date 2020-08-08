package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.model.repository.RoleRepository;
import projectsoft.cookingapp.data.model.repository.UserRepository;
import projectsoft.cookingapp.data.service.models.UserRegisterServiceModel;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.RoleService;
import projectsoft.cookingapp.data.service.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userRepository.findByUsername(username);
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public User register(UserRegisterServiceModel userRegisterServiceModel) {

        User user = this.modelMapper.map(userRegisterServiceModel, User.class);


        if (this.userRepository.count() == 0) {

            this.roleService.seedRolesInDb();

            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));

        } else {
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("USER"))));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {

        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

    }

    @Override
    public List<UserServiceModel> findAllUsers() {

        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class)).collect(Collectors.toList());

    }
}
