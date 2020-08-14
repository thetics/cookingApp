package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.Recipe;
import projectsoft.cookingapp.data.model.repository.RateRepository;
import projectsoft.cookingapp.data.model.repository.RecipeRepository;
import projectsoft.cookingapp.data.model.view.RecipeViewModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;
import projectsoft.cookingapp.errors.PostNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final ModelMapper modelMapper;
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final RateRepository rateRepository;

    public RecipeServiceImpl(ModelMapper modelMapper, RecipeRepository recipeRepository, CategoryService categoryService, RateRepository rateRepository) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.rateRepository = rateRepository;
    }

    @Override
    public RecipeServiceModel addItem(RecipeServiceModel recipeServiceModel) {

        Recipe recipe = this.modelMapper.map(recipeServiceModel, Recipe.class);
        recipe.setCategory(this.categoryService.getCategoryByName(recipeServiceModel.getCategory().getCategoryName()));

        return this.modelMapper.map(this.recipeRepository.saveAndFlush(recipe), RecipeServiceModel.class);


    }

    @Override
    public RecipeServiceModel createPost(RecipeServiceModel recipeServiceModel) {
        Recipe recipe = this.modelMapper.map(recipeServiceModel, Recipe.class);
        recipe.setCategory(this.categoryService.getCategoryByName(recipeServiceModel.getCategory().getCategoryName()));

        return this.modelMapper.map(this.recipeRepository.saveAndFlush(recipe), RecipeServiceModel.class);
    }

    @Override
    public List<RecipeViewModel> findAllItems() {
        return this.recipeRepository.findAll()
                .stream()
                .map(recipe -> {
                    RecipeViewModel recipeViewModel = this.modelMapper.map(recipe, RecipeViewModel.class);

                    recipeViewModel.setImageUrl(recipe.getImageUrl());

                    recipeViewModel.setCategoryName(recipe.getCategory().getCategoryName());

                    return recipeViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public RecipeServiceModel findById(String id) {
        Recipe recipe = this.recipeRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with the given id was not found"));

        return this.modelMapper.map(recipe, RecipeServiceModel.class);
    }

    @Override
    public void deleteOneById(String id) {

        this.recipeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.recipeRepository.deleteAll();
    }

    @Override
    public RecipeServiceModel editRecipe(String id, RecipeServiceModel recipeServiceModel) {
        Recipe recipe = this.recipeRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Recipe with the given id was not found"));

        recipe.setName(recipeServiceModel.getName());

        if (recipeServiceModel.getImageUrl().contains("org.springframework.web.multipart.support.StandardMultipartHttpServletRequest")){
            recipeServiceModel.setImageUrl(null);
        }
        if (recipeServiceModel.getImageUrl() != null) {
            System.out.println();
            recipe.setImageUrl(recipeServiceModel.getImageUrl());
        }
        recipe.setProducts(recipeServiceModel.getProducts());
        recipe.setDescription(recipeServiceModel.getDescription());
        recipe.setCategory(this.categoryService.getCategoryByName(recipeServiceModel.getCategory().getCategoryName()));

        return this.modelMapper.map(this.recipeRepository.saveAndFlush(recipe), RecipeServiceModel.class);
    }

    @Override
    public List<RecipeServiceModel> getAllUserRecipes(String id) {
        return this.recipeRepository.findByUploader_IdContainsOrderByPostTimeDesc(id)
                .stream()
                .map(p -> this.modelMapper.map(p, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}
