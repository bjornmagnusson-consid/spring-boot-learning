package com.bjornmagnusson.springbootlearning.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.bjornmagnusson.springbootlearning.model.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    
}
