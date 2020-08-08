package projectsoft.cookingapp.data.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectsoft.cookingapp.data.model.entity.Category;
import projectsoft.cookingapp.data.model.entity.CategoryName;
import projectsoft.cookingapp.data.model.repository.CategoryRepository;
import projectsoft.cookingapp.data.service.services.CategoryService;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        this.categoryRepository.save(new Category(categoryName,
                                String.format("Description for %s",
                                        categoryName.name())));
                    });
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(CategoryName categoryName) {
        return this.categoryRepository.getCategoryByCategoryName(categoryName).orElse(null);

    }
}
