package com.bjornmagnusson.springbootlearning.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.bjornmagnusson.springbootlearning.model.Product;

public interface ProductRepository extends ListCrudRepository<Product, Integer> {
    
}
