package com.deltacapita.shoppingcart;

import com.deltacapita.shoppingcart.dto.ItemsRequestDto;
import com.deltacapita.shoppingcart.webapps.ShoppingCartApplication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ItemsRequestDtoTest {

    @Test
    void test_validate_InvalidItem() {
        List<String> items = Arrays.asList("Apple", "Watermelon");
        ItemsRequestDto itemsRequestDto = new ItemsRequestDto(items);
        Exception exception = assertThrows(IllegalArgumentException.class, itemsRequestDto::validate);
        assertEquals("Invalid items: Watermelon cannot be purchased", exception.getMessage());
    }
}
