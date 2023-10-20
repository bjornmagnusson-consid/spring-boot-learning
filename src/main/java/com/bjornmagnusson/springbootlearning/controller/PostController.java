package com.bjornmagnusson.springbootlearning.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.repository.PostRepository;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> find(@PathVariable int id) {
        var postIfExist = repository.findById(id);
        if (postIfExist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(postIfExist.get());
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        LOGGER.info(post.toString());
        var postCreated = repository.save(post);
        return ResponseEntity.created(URI.create("/posts/" + postCreated.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> delete(@PathVariable int id) {
        LOGGER.info("Deleting post " + id);

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
