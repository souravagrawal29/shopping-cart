package com.deltacapita.shoppingcart.dto;

import java.util.Map;

public class ItemsResponseDto {

    public Map<String, Integer> items;

    public ItemsResponseDto(Map<String, Integer> items) {
        this.items = items;
    }
}
