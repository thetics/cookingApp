package projectsoft.cookingapp.data.service.services;


import projectsoft.cookingapp.data.model.entity.UserProfile;
import projectsoft.cookingapp.data.service.models.UserProfileServiceModel;

import java.util.List;

public interface UserProfileService {

    UserProfileServiceModel findById(String id);

    UserProfile findByUserId(String id);

    List<UserProfileServiceModel> findALl();
}
