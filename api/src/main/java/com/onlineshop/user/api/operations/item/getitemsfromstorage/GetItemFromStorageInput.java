package com.onlineshop.user.api.operations.item.getitemsfromstorage;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetItemFromStorageInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String itemId;
}
