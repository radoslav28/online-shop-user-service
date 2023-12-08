package com.onlineshop.user.core.converters.item;

import com.onlineshop.user.api.model.ItemFromStorageModel;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetItemFromStorageResultToItemFromStorageModel implements Converter<GetItemFromStorageResult, ItemFromStorageModel> {
    @Override
    public ItemFromStorageModel convert(GetItemFromStorageResult source) {
        return ItemFromStorageModel
                .builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .vendor(source.getVendor())
                .multimedia(source.getMultimedia())
                .tags(source.getTags())
                .quantity(source.getQuantity())
                .price(source.getPrice())
                .build();
    }
}
