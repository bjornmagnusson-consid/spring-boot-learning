package com.bjornmagnusson.springbootlearning;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.service.CartService;
import com.bjornmagnusson.springbootlearning.service.ProductService;

import jakarta.annotation.PostConstruct;

@Component
@ConditionalOnProperty(name = "testdata.enabled", havingValue = "true")
public class TestData {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestData.class);
    private ProductService productService;
    private CartService cartService;
    @Value("${testdata.number}")
    private int numberOfProducts;    

    public TestData(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @PostConstruct
    public void init() {
        var numberOfExistingProducts = productService.getAll().size();        
        if (numberOfExistingProducts == 0) {
            LOGGER.info("Adding {} products", numberOfProducts);
            for (int i = 1; i < this.numberOfProducts + 1; i++) {
                productService.createOrUpdate(new Product("name" + i, "description" + i));
            }
        }
        var numberOfProductsInCart = cartService.getCart().getProducts().stream().map(cartItem -> cartItem.getNumber()).count();
        if (numberOfProductsInCart == 0) {
            LOGGER.info("Adding products to cart");
            IntStream.of(1, 5, 9).forEach(id -> {
                var productOptional = productService.get(id);
                if (productOptional.isPresent()) {
                    cartService.addProduct(productOptional.get());
                }                
            });
        }
    }
}
