package projectsoft.cookingapp.validation.post;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import projectsoft.cookingapp.data.model.binding.RecipeAddBindingModel;
import projectsoft.cookingapp.data.model.repository.RecipeRepository;
import projectsoft.cookingapp.validation.ValidationConstants;
import projectsoft.cookingapp.validation.annotation.Validator;

@Validator
public class PostCreateValidator implements org.springframework.validation.Validator {

    private final RecipeRepository recipeRepository;

    @Autowired
    public PostCreateValidator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RecipeAddBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RecipeAddBindingModel recipeAddBindingModel = (RecipeAddBindingModel) o;

        if (recipeAddBindingModel.getName().length() < 3) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.POST_NAME_LENGTH,
                    ValidationConstants.POST_NAME_LENGTH
            );
        }

        if (recipeAddBindingModel.getImage().isEmpty()) {
            errors.rejectValue(
                    "image",
                    ValidationConstants.POST_IMAGE,
                    ValidationConstants.POST_IMAGE
            );
        }

        try {
            if (recipeAddBindingModel.getImage().getSize() > 1572864) {
                errors.rejectValue(
                        "image",
                        ValidationConstants.POST_IMAGE_LARGER,
                        ValidationConstants.POST_IMAGE_LARGER
                );
            }
        } catch (Exception ignored) {

        }

        if (recipeAddBindingModel.getProducts().length() < 5) {
            errors.rejectValue(
                    "products",
                    ValidationConstants.POST_PRODUCTS_LENGTH,
                    ValidationConstants.POST_PRODUCTS_LENGTH
            );
        }

        if (recipeAddBindingModel.getDescription().length() < 15) {
            errors.rejectValue(
                    "description",
                    ValidationConstants.POST_DESCRIPTION_LENGTH,
                    ValidationConstants.POST_DESCRIPTION_LENGTH
            );
        }
    }


}
