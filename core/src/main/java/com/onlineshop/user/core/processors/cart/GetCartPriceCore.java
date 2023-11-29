package com.onlineshop.user.core.processors.cart;

import com.onlineshop.user.api.model.CartItemModel;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartInput;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartOperation;
import com.onlineshop.user.api.operations.cartitem.getcartprice.GetCartPriceInput;
import com.onlineshop.user.api.operations.cartitem.getcartprice.GetCartPriceOperation;
import com.onlineshop.user.api.operations.cartitem.getcartprice.GetCartPriceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCartPriceCore implements GetCartPriceOperation {

    private final GetCartOperation getCart;

    @Override
    public GetCartPriceResult process(GetCartPriceInput input) {

        BigDecimal fullPrice = BigDecimal.valueOf(0);
        List<CartItemModel> cartItems = getCart.process(new GetCartInput(input.getUserId())).getCartItems();
        for (CartItemModel ci : cartItems) {
            fullPrice = fullPrice.add(ci.getPrice());
        }

        return GetCartPriceResult
                .builder()
                .fullPrice(fullPrice)
                .build();
    }
}
