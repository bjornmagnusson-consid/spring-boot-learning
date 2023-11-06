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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjornmagnusson.springbootlearning.model.Category;
import com.bjornmagnusson.springbootlearning.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        LOGGER.info("GET /api/categories");
        var categories = service.getAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> find(@PathVariable int id) {
        LOGGER.info("GET /api/categories/" + id);      
        var categoryIfExists = service.get(id);
        if (categoryIfExists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryIfExists.get());
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        LOGGER.info("POST /api/categories");
        var categoryCreated = service.createOrUpdate(category);
        return ResponseEntity.created(URI.create("/api/categories/" + categoryCreated.getId())).build();        
    }

    @PutMapping
    public ResponseEntity<Category> update(@RequestBody Category category) {
        LOGGER.info("PUT /api/categories");
        var categoryUpdated = service.createOrUpdate(category);
        return ResponseEntity.created(URI.create("/api/categories/" + categoryUpdated.getId())).build();        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable int id) {
        LOGGER.info("GET /api/categories/" + id);      
        service.delete(id);
        return ResponseEntity.status(204).build();
    }
}
