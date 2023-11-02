package com.bjornmagnusson.springbootlearning.controller;

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

import com.bjornmagnusson.springbootlearning.model.Cart;
import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.service.CartService;
import com.bjornmagnusson.springbootlearning.service.ProductService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    private CartService cartService;
    private ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Cart> get() {
        LOGGER.info("GET /api/cart");        
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/products")
    public ResponseEntity<Cart> addProduct(@RequestBody Product product) {
        LOGGER.info("POST /api/cart/products");
        cartService.addProduct(product);
        return ResponseEntity.noContent().build();        
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Cart> removeProduct(@PathVariable int id) {   
        LOGGER.info("DELETE /api/cart/products/{}", id);
        cartService.removeProduct(id);
        return ResponseEntity.status(204).build();
    }
}
