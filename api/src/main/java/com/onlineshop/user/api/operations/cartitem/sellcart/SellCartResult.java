package com.onlineshop.user.api.operations.cartitem.sellcart;

import com.onlineshop.storage.api.model.SaleModel;
import com.onlineshop.user.api.base.ProcessorResult;
import com.onlineshop.user.api.model.UserModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellCartResult implements ProcessorResult {
    private UserModel user;
    private List<SaleModel> sales;
}
