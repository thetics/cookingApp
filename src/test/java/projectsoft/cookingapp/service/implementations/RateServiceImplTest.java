package projectsoft.cookingapp.service.implementations;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import projectsoft.cookingapp.base.TestBase;
import projectsoft.cookingapp.data.model.entity.Rate;
import projectsoft.cookingapp.data.model.entity.User;
import projectsoft.cookingapp.data.model.repository.RateRepository;
import projectsoft.cookingapp.data.service.services.RateService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RateServiceImplTest extends TestBase {

    @MockBean
    RateRepository rateRepository;

    @MockBean
    RateService rateService;

    @Autowired
    RateService service;

    @Test
    void getRate_whenRate_shouldReturnUserId() {
        Rate rate = new Rate();
        rate.setId("2");
        this.rateRepository.saveAndFlush(rate);
        assertEquals("2", rate.getId());
    }

    @Test
    void getRate_whenUseHasNotRate_shouldReturnFalse() {
        Rate rate = new Rate();
        rate.setId("1");

        User user = new User();
        user.setId("2");

        boolean hasRate = service.userHasRated(rate.getId(), user.getId());

        assertFalse(hasRate);
    }

}