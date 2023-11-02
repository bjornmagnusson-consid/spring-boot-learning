package com.bjornmagnusson.springbootlearning.repository;

import org.springframework.data.repository.CrudRepository;

import com.bjornmagnusson.springbootlearning.model.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    
}
