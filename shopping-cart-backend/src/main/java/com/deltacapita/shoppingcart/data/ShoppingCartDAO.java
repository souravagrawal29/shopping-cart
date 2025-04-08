package com.deltacapita.shoppingcart.data;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ShoppingCartDAO {

    private final Map<String, Integer> shoppingCart;

    public ShoppingCartDAO() {
        shoppingCart = new ConcurrentHashMap<>();
    }

    public void addItems(List<String> items) {
        for (String item : items) {
            shoppingCart.merge(item, 1, Integer::sum);
        }
    }

    public Map<String, Integer> getItems() {
        synchronized (shoppingCart) {
            return new HashMap<>(shoppingCart);
        }
    }

    public void clearCart() {
        synchronized (shoppingCart) {
            shoppingCart.clear();
        }
    }

}
