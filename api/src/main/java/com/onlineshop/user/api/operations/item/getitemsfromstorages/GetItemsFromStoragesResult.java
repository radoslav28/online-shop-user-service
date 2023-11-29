package com.onlineshop.user.api.operations.item.getitemsfromstorages;

import com.onlineshop.user.api.base.ProcessorResult;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetItemsFromStoragesResult implements ProcessorResult {
    List<ItemFromStorageModel> itemsFromStorage;
}
