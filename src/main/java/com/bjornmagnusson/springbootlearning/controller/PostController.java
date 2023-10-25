package com.bjornmagnusson.springbootlearning.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {    
    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        var posts = service.getAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> find(@PathVariable int id) {        
        var postIfExist = service.get(id);
        if (postIfExist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(postIfExist.get());
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        var postCreated = service.create(post);
        return ResponseEntity.created(URI.create("/api/posts/" + postCreated.getId())).build();        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> delete(@PathVariable int id) {        
        service.delete(id);
        return ResponseEntity.status(204).build();
    }
}
