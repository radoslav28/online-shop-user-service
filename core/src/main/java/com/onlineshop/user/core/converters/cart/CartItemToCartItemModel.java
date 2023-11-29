package com.onlineshop.user.core.converters.cart;

import com.onlineshop.user.api.model.CartItemModel;
import com.onlineshop.user.persistence.entities.CartItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartItemToCartItemModel implements Converter<CartItem, CartItemModel> {
    @Override
    public CartItemModel convert(CartItem source) {
        return CartItemModel
                .builder()
                .id(String.valueOf(source.getId()))
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
