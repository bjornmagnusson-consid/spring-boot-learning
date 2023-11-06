package com.bjornmagnusson.springbootlearning.model;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    public Integer getId() {
        return id;
    }

    @JsonProperty("products")
    @OneToMany(mappedBy="cart", fetch = FetchType.EAGER)
    Set<CartItem> products = new HashSet<>();
    
    public void setProducts(Set<CartItem> products) {
        this.products = products;
    }

    public Set<CartItem> getProducts() {
        return products;
    }
}
