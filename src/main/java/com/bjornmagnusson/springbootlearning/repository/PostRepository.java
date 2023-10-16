package com.bjornmagnusson.springbootlearning.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.bjornmagnusson.springbootlearning.model.Post;

public interface PostRepository extends ListCrudRepository<Post, Integer> {
    
}
