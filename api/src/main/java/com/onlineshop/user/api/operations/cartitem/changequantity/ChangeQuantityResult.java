package com.onlineshop.user.api.operations.cartitem.changequantity;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ChangeQuantityResult implements ProcessorResult {
    private final String message = "Quantity changed successfully";
}
