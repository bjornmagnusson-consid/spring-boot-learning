package com.bjornmagnusson.springbootlearning.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartItem(@JsonProperty("id") Integer productId, @JsonProperty("number") Integer number) {}
