package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.UserProfile;
import projectsoft.cookingapp.data.model.repository.UserProfileRepository;
import projectsoft.cookingapp.data.service.models.UserProfileServiceModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserProfileServiceModel findById(String id) {
        return this.userProfileRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserProfileServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserProfile findByUserId(String id) {
        return this.userProfileRepository.findByUserId(id);

    }

    @Override
    public List<UserProfileServiceModel> findALl() {
        return this.userProfileRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserProfileServiceModel.class))
                .collect(Collectors.toList());
    }
}
