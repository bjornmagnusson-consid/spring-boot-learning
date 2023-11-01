package com.bjornmagnusson.springbootlearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjornmagnusson.springbootlearning.model.Cart;
import com.bjornmagnusson.springbootlearning.model.CartItem;
import com.bjornmagnusson.springbootlearning.model.Product;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private Cart cart = new Cart();

    @GetMapping
    public ResponseEntity<List<CartItem>> get() {
        return ResponseEntity.ok(cart.getCart());
    }

    @PostMapping("/products")
    public ResponseEntity<Cart> addProduct(@RequestBody Product product) {
        cart.addProduct(product);
        return ResponseEntity.noContent().build();        
    }

    @DeleteMapping("/products")
    public ResponseEntity<Cart> removeProduct(@RequestBody Product product) {        
        cart.removeProduct(product);
        return ResponseEntity.status(204).build();
    }
}
