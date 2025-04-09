package com.deltacapita.shoppingcart.dto;

import com.deltacapita.shoppingcart.businessLogic.ItemType;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ItemsRequestDto {

    public List<String> items;

    public ItemsRequestDto() {
    }

    public ItemsRequestDto(List<String> items) {
        this.items = items;
    }

    public void validate() {
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("Items list cannot be empty");
        }
        StringBuilder invalidItems = new StringBuilder();
        for (String item : items) {
            try {
                ItemType.get(item);
            } catch (IllegalStateException e) {
                invalidItems.append(item).append(',');
            }
        }
        if (invalidItems.length() != 0) {
            invalidItems.deleteCharAt(invalidItems.length() - 1);
            throw new IllegalArgumentException("Invalid items: " + invalidItems + " cannot be purchased");
        }
    }
}
