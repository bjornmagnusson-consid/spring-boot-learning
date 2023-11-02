package com.bjornmagnusson.springbootlearning.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {    
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        var products = service.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable int id) {        
        var productIfExists = service.get(id);
        if (productIfExists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productIfExists.get());
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        var productCreated = service.createOrUpdate(product);
        return ResponseEntity.created(URI.create("/api/products/" + productCreated.getId())).build();        
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        var productCreated = service.createOrUpdate(product);
        return ResponseEntity.created(URI.create("/api/products/" + productCreated.getId())).build();        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable int id) {        
        service.delete(id);
        return ResponseEntity.status(204).build();
    }
}
