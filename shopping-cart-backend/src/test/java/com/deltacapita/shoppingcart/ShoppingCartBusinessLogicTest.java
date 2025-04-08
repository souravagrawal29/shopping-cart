package com.deltacapita.shoppingcart;

import com.deltacapita.shoppingcart.webapps.ShoppingCartApplication;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deltacapita.shoppingcart.businessLogic.ShoppingCartBusinessLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ShoppingCartApplication .class)
public class ShoppingCartBusinessLogicTest {

    @Autowired
    private ShoppingCartBusinessLogic shoppingCartBusinessLogic;

    @BeforeEach
    void setup() {
        shoppingCartBusinessLogic.deleteCart();
    }

    @Test
     void test_addItems() {
        List<String> items = Arrays.asList("Apple", "Melon", "Lime", "Lime", "Apple", "Banana");
        shoppingCartBusinessLogic.addItems(items);
        Map<String, Integer> itemsWithCount = shoppingCartBusinessLogic.getItems().items;
        assertEquals(2, itemsWithCount.get("Apple"));
        assertEquals(2, itemsWithCount.get("Lime"));
        assertEquals(1, itemsWithCount.get("Melon"));
        assertEquals(1, itemsWithCount.get("Banana"));
    }

    @Test
    void test_calculateTotalPrice() {
        List<String> items = Arrays.asList("Apple", "Melon", "Melon", "Lime", "Lime", "Apple", "Banana", "Melon", "Lime", "Lime", "Melon", "Melon");
        shoppingCartBusinessLogic.addItems(items);
        BigDecimal totalPriceForApples = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(0.35));
        BigDecimal totalPriceForBananas = BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(0.20));
        BigDecimal totalPriceForMelons = BigDecimal.valueOf(((5 / 2) + 5 % 2)).multiply(BigDecimal.valueOf(0.50));
        BigDecimal totalPriceForLimes = BigDecimal.valueOf((((4 / 3) * 2) + 4 % 3)).multiply(BigDecimal.valueOf(0.15));
        BigDecimal totalPrice = totalPriceForApples.add(totalPriceForBananas).add(totalPriceForMelons).add(totalPriceForLimes);
        assertEquals(totalPrice, shoppingCartBusinessLogic.calculateTotalPrice().totalPrice);
    }

}
