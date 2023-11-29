package com.onlineshop.user.core.converters.item;

import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemModelToItemFromStorageModel implements Converter<ItemModel, ItemFromStorageModel> {
    @Override
    public ItemFromStorageModel convert(ItemModel source) {
        return ItemFromStorageModel
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
