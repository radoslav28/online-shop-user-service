package com.onlineshop.user.api.operations.cartitem.emptycart;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class EmptyCartResult implements ProcessorResult {
    private final String message = "Your cart is empty";
}
