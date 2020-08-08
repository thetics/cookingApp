package projectsoft.cookingapp.data.service.services;

import projectsoft.cookingapp.data.service.models.RateServiceModel;
import projectsoft.cookingapp.data.service.models.RecipeServiceModel;

public interface RateService {
    void create(RateServiceModel rateServiceModel, RecipeServiceModel recipeServiceModel);



//    RateServiceModel rate(String post_id, String user_id);

    boolean userHasRated(String rate_id, String user_id);

//    RateServiceModel findRateByPostId(String id);
}
