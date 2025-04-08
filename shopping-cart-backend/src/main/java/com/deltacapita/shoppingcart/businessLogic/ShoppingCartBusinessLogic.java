package com.deltacapita.shoppingcart.businessLogic;

import com.deltacapita.shoppingcart.data.ShoppingCartDAO;
import com.deltacapita.shoppingcart.dto.CartTotalResponseDto;
import com.deltacapita.shoppingcart.dto.ItemsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartBusinessLogic {

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    public void addItems(List<String> items) {
        shoppingCartDAO.addItems(items);
    }

    public ItemsResponseDto getItems() {
        return new ItemsResponseDto(shoppingCartDAO.getItems());
    }

    public CartTotalResponseDto calculateTotalPrice() {
        Map<String, Integer> itemsWithCount = shoppingCartDAO.getItems();
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<String, Integer> entry : itemsWithCount.entrySet()) {
            ItemType item = ItemType.get(entry.getKey());
            int quantity = entry.getValue();
            totalPrice = totalPrice.add(item.calculateTotalPrice(quantity));
        }
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        return new CartTotalResponseDto(totalPrice, itemsWithCount);
    }

    public void deleteCart() {
        shoppingCartDAO.clearCart();
    }


}
