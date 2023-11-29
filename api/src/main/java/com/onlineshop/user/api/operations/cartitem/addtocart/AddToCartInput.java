package com.onlineshop.user.api.operations.cartitem.addtocart;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddToCartInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String userId;

    @UUID
    @NotBlank
    private String itemId;

    @NotNull
    @Positive
    private Long quantity;
}
