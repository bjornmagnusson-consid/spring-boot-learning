package com.bjornmagnusson.springbootlearning.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.repository.PostRepository;

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
