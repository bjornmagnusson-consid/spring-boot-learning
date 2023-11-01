package com.bjornmagnusson.springbootlearning.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cart.class);
    @JsonProperty("products")
    private final Map<Integer, Integer> productsIdToNumber = new HashMap<>();

    public List<CartItem> getCart() {
        return productsIdToNumber.entrySet().stream()
            .map(entry -> new CartItem(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public void addProduct(Product product) {        
        var number = productsIdToNumber.getOrDefault(product.getId(), 0) + 1;
        LOGGER.info("Product ({}) increased to {}", product, number);
        productsIdToNumber.put(product.getId(), number);
    }

    public void removeProduct(Product product) {
        Integer numberOfExistingProducts = productsIdToNumber.get(product.getId());
        var number = numberOfExistingProducts - 1;
        if (number == 0) {
            productsIdToNumber.remove(product.getId());
            LOGGER.info("Product ({}) removed", product);
        } else {
            productsIdToNumber.put(product.getId(), number);
            LOGGER.info("Product ({}) increased to {}", product, number);
        }        
    }
}
