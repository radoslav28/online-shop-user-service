package com.onlineshop.user.api.operations.cartitem.sellcart;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellCartInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String userId;
}
