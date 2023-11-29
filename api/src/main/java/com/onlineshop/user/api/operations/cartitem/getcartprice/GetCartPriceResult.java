package com.onlineshop.user.api.operations.cartitem.getcartprice;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCartPriceResult implements ProcessorResult {
    private BigDecimal fullPrice;
}
