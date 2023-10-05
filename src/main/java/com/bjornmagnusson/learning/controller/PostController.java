package com.bjornmagnusson.learning.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjornmagnusson.learning.model.Post;
import com.bjornmagnusson.learning.repository.PostRepository;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Post> findAll() {
        return repository.findAll();
    }
}
