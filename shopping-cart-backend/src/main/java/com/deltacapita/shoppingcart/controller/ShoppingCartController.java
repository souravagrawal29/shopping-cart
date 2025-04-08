package com.deltacapita.shoppingcart.controller;

import com.deltacapita.shoppingcart.businessLogic.ShoppingCartBusinessLogic;
import com.deltacapita.shoppingcart.dto.CartTotalResponseDto;
import com.deltacapita.shoppingcart.dto.ErrorDto;
import com.deltacapita.shoppingcart.dto.ItemsRequestDto;
import com.deltacapita.shoppingcart.dto.ItemsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartBusinessLogic shoppingCartBusinessLogic;

    @GetMapping
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Shopping Cart Backend is up and running");
    }

    @PostMapping(value = "/api/cart/items")
    public ResponseEntity<?> addItems(@RequestBody ItemsRequestDto requestDto) {
        try {
            requestDto.validate();
            shoppingCartBusinessLogic.addItems(requestDto.items);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
        }
    }

    @GetMapping(value = "/api/cart/items")
    public ItemsResponseDto getItems() {
        return shoppingCartBusinessLogic.getItems();
    }

    @GetMapping(value = "/api/cart/total")
    public CartTotalResponseDto getTotalPrice() {
        return shoppingCartBusinessLogic.calculateTotalPrice();
    }

    @DeleteMapping(value = "/api/cart")
    public void deleteCart() { // Added for testing
        shoppingCartBusinessLogic.deleteCart();
    }
}
