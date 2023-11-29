package com.onlineshop.user.core.converters.item;

import com.onlineshop.storage.api.model.StorageModel;
import com.onlineshop.storage.api.operations.storage.get.byitem.GetStorageByItemResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetStorageByItemResultToStorageModel implements Converter<GetStorageByItemResult, StorageModel> {
    @Override
    public StorageModel convert(GetStorageByItemResult source) {
        return StorageModel
                .builder()
                .id(source.getId())
                .itemId(source.getItemId())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
