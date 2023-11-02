package com.bjornmagnusson.springbootlearning.repository;

import org.springframework.data.repository.CrudRepository;

import com.bjornmagnusson.springbootlearning.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    
}
