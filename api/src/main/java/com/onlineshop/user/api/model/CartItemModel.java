package com.onlineshop.user.api.model;

import com.onlineshop.store.api.model.ItemModel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemModel {
    private String id;
    private ItemModel item;
    private BigDecimal price;
    private Long quantity;
}
