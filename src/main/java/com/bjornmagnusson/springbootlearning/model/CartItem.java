package com.bjornmagnusson.springbootlearning.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    Integer productId;
    Integer number;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartItem() {
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public CartItem(Integer productId, Integer number) {
        this.productId = productId;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "CartItem [id=" + id + ", productId=" + productId + ", number=" + number + ", cart=" + cart + "]";
    }
}
