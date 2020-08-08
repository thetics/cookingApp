package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.Recipe;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.model.repository.RateRepository;
import projectsoft.cookingapp.data.model.repository.RecipeRepository;
import projectsoft.cookingapp.data.model.repository.UserRepository;
import projectsoft.cookingapp.data.service.models.RateServiceModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;
import projectsoft.cookingapp.data.service.services.RateService;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public RateServiceImpl(RateRepository rateRepository, RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.rateRepository = rateRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(RateServiceModel rateServiceModel, RecipeServiceModel recipeServiceModel) {
        Recipe recipe = this.modelMapper.map(this.recipeRepository.findByImageUrl(recipeServiceModel.getImageUrl()), Recipe.class);
        Rate rate = this.modelMapper.map(rateServiceModel, Rate.class);

        rate.setRecipe(recipe);
        this.modelMapper.map(this.rateRepository.saveAndFlush(rate), RateServiceModel.class);

        recipe.setRate(rate);
        this.modelMapper.map(this.recipeRepository.saveAndFlush(recipe), RecipeServiceModel.class);
    }

    @Override
    public boolean userHasRated(String rate_id, String user_id) {

        User user = this.userRepository.findUserById(user_id);
        Rate rate = this.rateRepository.findByIdAndUserContains(rate_id, user);

        return rate != null;
    }
}
