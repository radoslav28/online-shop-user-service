package com.onlineshop.user.api.operations.cartitem.removefromcart;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveFromCartInput implements ProcessorInput {

    private String userId;

    @UUID
    @NotBlank
    private String itemId;
}
