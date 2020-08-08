package projectsoft.cookingapp.service.implementations;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import projectsoft.cookingapp.base.TestBase;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.model.repository.UserRepository;
import projectsoft.cookingapp.data.service.models.UserServiceModel;
import projectsoft.cookingapp.data.service.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceImplTest extends TestBase {
    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @Autowired
    UserService service;

    @Test
    void getUser_whenNoUser_shouldReturnEmptyList() {
        List<UserServiceModel> userServiceModels = service.findAllUsers();
        assertEquals(0, userServiceModels.size());
    }

    @Test
    void getUser_whenUser_shouldReturnUserId() {
        User user = new User();
        user.setId("1");
        this.userRepository.saveAndFlush(user);

        assertEquals("1", user.getId());
    }
}