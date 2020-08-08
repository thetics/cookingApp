package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.SavedRecipe;
import projectsoft.cookingapp.data.model.repository.SavedRecipeRepository;
import projectsoft.cookingapp.data.service.models.SavedRecipeServiceModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedRecipeServiceImpl implements SavedRecipeService {
    private final SavedRecipeRepository savedRecipeRepository;
    private final ModelMapper modelMapper;

    public SavedRecipeServiceImpl(SavedRecipeRepository savedRecipeRepository, ModelMapper modelMapper) {
        this.savedRecipeRepository = savedRecipeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveRecipe(SavedRecipeServiceModel savedRecipeServiceModel) {
        SavedRecipe savedRecipe = this.modelMapper.map(savedRecipeServiceModel, SavedRecipe.class);
        this.savedRecipeRepository.saveAndFlush(savedRecipe);
    }

    @Override
    public void delete(String id) {
        SavedRecipe savedRecipe = this.savedRecipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with the given id was not found"));
        this.savedRecipeRepository.delete(savedRecipe);
    }

    @Override
    public List<SavedRecipeServiceModel> findAll(String id) {
        return this.savedRecipeRepository.findByUser_IdContains(id)
                .stream()
                .map(r -> this.modelMapper.map(r, SavedRecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}

