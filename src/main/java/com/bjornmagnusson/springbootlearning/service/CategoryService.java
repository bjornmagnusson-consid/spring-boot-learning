package com.bjornmagnusson.springbootlearning.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjornmagnusson.springbootlearning.model.Category;
import com.bjornmagnusson.springbootlearning.repository.CategoryRepository;

@Service
public class CategoryService {
    private CategoryRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        LOGGER.info("Load all categories");
        return repository.findAll();
    }

    public Optional<Category> get(int id) {
        LOGGER.info("Load category (id={})", id);
        return repository.findById(id);
    }

    public Category createOrUpdate(Category category) {
        var action = "Creating category";
        if (Optional.ofNullable(category.getId()).isPresent()) {
            var existingProduct = repository.findById(category.getId());        
            if (existingProduct.isPresent()) {
                action = "Updating category with id " + existingProduct.get().getId();
            }
        }
        LOGGER.info("{} (name={})", action, category.getName());
        return repository.save(category);
    }

    public void delete(int id) {
        LOGGER.info("Deleting category (id={})", id);
        repository.deleteById(id);
    }    
}
