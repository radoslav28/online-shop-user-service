package com.onlineshop.user.core.converters.cart;

import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartInput;
import com.onlineshop.user.persistence.entities.CartItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddToCartInputToCartItem implements Converter<AddToCartInput, CartItem> {
    @Override
    public CartItem convert(AddToCartInput source) {
        return CartItem
                .builder()
                .userId(UUID.fromString(source.getUserId()))
                .itemId(UUID.fromString(source.getItemId()))
                .quantity(source.getQuantity())
                .build();
    }
}
