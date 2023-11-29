package com.onlineshop.user.api.operations.cartitem.removefromcart;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class RemoveFromCartResult implements ProcessorResult {
    private final String message = "Item successfully removed!";
}
