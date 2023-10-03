package com.bjornmagnusson.learning.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.bjornmagnusson.learning.model.Post;

public interface PostRepository extends ListCrudRepository<Post, Integer> {
    
}
