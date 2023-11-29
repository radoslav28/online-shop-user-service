package com.onlineshop.user.core.converters.item;

import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemFromStorageModelToItemModel implements Converter<ItemFromStorageModel, ItemModel> {
    @Override
    public ItemModel convert(ItemFromStorageModel source) {
        return ItemModel
                .builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .vendor(source.getVendor())
                .multimedia(source.getMultimedia())
                .tags(source.getTags())
                .build();
    }
}
