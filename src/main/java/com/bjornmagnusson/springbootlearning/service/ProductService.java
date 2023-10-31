package com.bjornmagnusson.springbootlearning.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        LOGGER.info("Load all products");
        return repository.findAll();
    }

    public Optional<Product> get(int id) {
        LOGGER.info("Load products (id={})", id);
        return repository.findById(id);
    }

    public Product create(Product product) {
        LOGGER.info("Creating products (name={}, description={})", product.getName(), product.getDescription());
        return repository.save(product);
    }

    public void delete(int id) {
        LOGGER.info("Deleting products (id={})", id);
        repository.deleteById(id);
    }
}
