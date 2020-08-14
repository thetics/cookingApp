package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.*;
import projectsoft.cookingapp.data.model.repository.*;
import projectsoft.cookingapp.data.service.models.UserServiceModel;

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
    private final UserProfileRepository userProfileRepository;
    private final RecipeRepository recipeRepository;
  private final SavedRecipeRepository savedRecipeRepository;
  private final RateRepository rateRepository;
  private final NoteRepository noteRepository;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserProfileRepository userProfileRepository, RecipeRepository recipeRepository, SavedRecipeRepository savedRecipeRepository, RateRepository rateRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.recipeRepository = recipeRepository;
        this.savedRecipeRepository = savedRecipeRepository;
        this.rateRepository = rateRepository;
        this.noteRepository = noteRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userRepository.findByUsername(username);
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public User register(UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);


        if (this.userRepository.count() == 0) {

            this.roleService.seedRolesInDb();

            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));

        } else {
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("USER"))));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
       this.modelMapper.map(this.userProfileRepository.saveAndFlush(userProfile),UserServiceModel.class);
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

    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect id!"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("USER"));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("USER"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ADMIN"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public void editUserProfile(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (userServiceModel.getPassword() != null) {
            user.setPassword(userServiceModel.getPassword() != null ?
                    this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                    user.getPassword());
        }

        UserProfile userProfile = this.userProfileRepository.findByUserId(user.getId());
        if (userServiceModel.getFirstName() != null) {
            userProfile.setFirstName(userServiceModel.getFirstName());
        }
        if (userServiceModel.getLastName() != null) {
            userProfile.setLastName(userServiceModel.getLastName());
        }
        if (userServiceModel.getImageUrl() != null) {
            userProfile.setImgUrl(userServiceModel.getImageUrl());
        }

        this.modelMapper.map(this.userProfileRepository.saveAndFlush(userProfile), UserServiceModel.class);
        this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findUserById(id);
        UserProfile userProfile = this.userProfileRepository.findByUserId(id);
        List<SavedRecipe> savedRecipes = this.savedRecipeRepository.findByUser_IdContains(id);
//        List<Group> groups = this.groupRepository.findAllByUsers_IdContains(id);
        List<Rate> rates = this.rateRepository.findAllByUser_IdContains(id);
        List<Note> notes = this.noteRepository.findAllByUser_IdContains(id);
        List<Recipe> recipes = this.recipeRepository.findByUploader_IdContainsOrderByPostTimeDesc(id);






        if (!savedRecipes.isEmpty()) {
            this.savedRecipeRepository.deleteAll(savedRecipes);
        }

//        if (!groups.isEmpty()) {
//            for (Group group : groups) {
//                if (group.getUsers().contains(user)) {
//                    group.getUsers().remove(user);
//                }
//            }
//        }

        if (!rates.isEmpty()) {
            for (Rate rate : rates) {
                rate.getUser().remove(user);
            }
        }


        if (!recipes.isEmpty()) {
            for (Recipe recipe : recipes) {
                List<SavedRecipe> savedRec = this.savedRecipeRepository.findAllByRecipe_IdContains(recipe.getId());

                if (!savedRec.isEmpty()) {
                    this.savedRecipeRepository.deleteAll(savedRec);
                }


                if (recipe.getRate().getCount() > 0) {
                    Rate rate = this.rateRepository.findByRecipe_idContains(recipe.getId());
                    this.rateRepository.delete(rate);
                }
            }
            this.recipeRepository.deleteAll(recipes);
        }

        if (!notes.isEmpty()) {
            this.noteRepository.deleteAll();
        }

        user.getAuthorities().remove(user);
        this.userProfileRepository.delete(userProfile);
        this.userRepository.delete(user);
    }
}
