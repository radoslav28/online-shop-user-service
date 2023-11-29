package com.onlineshop.user.api.operations.cartitem.addtocart;

import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.user.api.base.ProcessorResult;
import com.onlineshop.user.api.model.UserModel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddToCartResult implements ProcessorResult {
    private String id;
    private UserModel user;
    private ItemModel item;
    private BigDecimal price;
    private Long quantity;
}
