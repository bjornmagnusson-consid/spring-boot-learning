package com.bjornmagnusson.springbootlearning.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjornmagnusson.springbootlearning.controller.PostController;
import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.repository.PostRepository;

@Service
public class PostService {
    private PostRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> getAll() {
        LOGGER.info("Load all posts");
        return repository.findAll();
    }

    public Optional<Post> get(int id) {
        LOGGER.info("Load post (id={})", id);
        return repository.findById(id);
    }

    public Post create(Post post) {
        LOGGER.info("Creating post (title={}, body={})", post.getTitle(), post.getBody());
        return repository.save(post);
    }

    public void delete(int id) {
        LOGGER.info("Deleting post (id={})", id);
        repository.deleteById(id);
    }
}
