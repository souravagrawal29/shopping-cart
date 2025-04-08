package com.deltacapita.shoppingcart.businessLogic;

import java.math.BigDecimal;

public enum ItemType {

    APPLE("Apple", 0.35)
  , BANANA("Banana", 0.20)
  , MELON("Melon", 0.50)
  , LIME("Lime", 0.15)
  ;

    public final String name;
    public final BigDecimal price;


    ItemType(String name, double price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public static ItemType get(String item) {
        for (ItemType itemType : values()) {
            if (itemType.name().equals(item.toUpperCase())) {
                return itemType;
            }
        }
        throw new IllegalStateException("Unknown item type: " + item);
    }

    public BigDecimal calculateTotalPrice(int quantity) {
        switch (this) {
            case MELON:
                int discountedMelons = quantity / 2;
                int remainingMelons = quantity % 2;
                return price.multiply(BigDecimal.valueOf(discountedMelons + remainingMelons));
            case LIME:
                int discountedLimes = (quantity / 3) * 2;
                int remainingLimes = quantity % 3;
                return price.multiply(BigDecimal.valueOf(discountedLimes + remainingLimes));
            default:
                return price .multiply(BigDecimal.valueOf(quantity));
        }
    }
}
