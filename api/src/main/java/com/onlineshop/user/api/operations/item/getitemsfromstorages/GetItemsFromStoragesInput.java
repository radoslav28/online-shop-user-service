package com.onlineshop.user.api.operations.item.getitemsfromstorages;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetItemsFromStoragesInput implements ProcessorInput {

    private List<@UUID @NotBlank String> itemIds;
}
