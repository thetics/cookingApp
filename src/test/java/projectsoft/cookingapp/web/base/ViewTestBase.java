package projectsoft.cookingapp.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import projectsoft.cookingapp.base.TestBase;

@AutoConfigureMockMvc
public class ViewTestBase extends TestBase {
    @Autowired
    protected MockMvc mockMvc;
}