package com.onlineshop.user.api.operations.cartitem.getcart;

import com.onlineshop.user.api.base.ProcessorResult;
import com.onlineshop.user.api.model.CartItemModel;
import com.onlineshop.user.api.model.UserModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCartResult implements ProcessorResult {
    private UserModel user;
    private List<CartItemModel> cartItems;
}
