package com.bjornmagnusson.springbootlearning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cart.class);
    
    @JsonIgnore
    private final Map<Integer, Integer> productsIdToNumber = new HashMap<>();
    @JsonProperty("products")
    private List<CartItem> products = new ArrayList<>();
    
    public List<CartItem> getProducts() {
        return products;
    }

    public void update() {
        products = productsIdToNumber.entrySet().stream()
            .map(entry -> new CartItem(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public void addProduct(Product product) {        
        var number = productsIdToNumber.getOrDefault(product.getId(), 0) + 1;
        LOGGER.info("Product ({}) increased to {}", product, number);
        productsIdToNumber.put(product.getId(), number);
        update();
    }

    public void removeProduct(int id) {
        Integer numberOfExistingProducts = productsIdToNumber.get(id);
        var number = numberOfExistingProducts - 1;
        if (number == 0) {
            productsIdToNumber.remove(id);
            LOGGER.info("Product ({}) removed", id);
        } else {
            productsIdToNumber.put(id, number);
            LOGGER.info("Product ({}) increased to {}", id, number);
        }
        update();
    }
}
