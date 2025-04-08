package com.deltacapita.shoppingcart.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CartTotalResponseDto extends ItemsResponseDto {

    public BigDecimal totalPrice;

    public CartTotalResponseDto(BigDecimal totalPrice, Map<String, Integer> items) {
        super(items);
        this.totalPrice = totalPrice;
    }
}
