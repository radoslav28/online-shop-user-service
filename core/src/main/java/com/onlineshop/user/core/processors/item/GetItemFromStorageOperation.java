package com.onlineshop.user.core.processors.item;

import com.onlineshop.storage.api.model.StorageModel;
import com.onlineshop.storage.restexport.StorageServiceRestClient;
import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.store.restexport.StoreServiceRestClient;
import com.onlineshop.user.api.exceptions.*;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageInput;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageResult;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetItemFromStorageOperation implements com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageOperation {

    private final StoreServiceRestClient storeServiceRestClient;
    private final StorageServiceRestClient storageServiceRestClient;
    private final ConversionService conversionService;

    @Override
    public GetItemFromStorageResult process(GetItemFromStorageInput input) {

        try {

            ItemModel item = conversionService.convert(storeServiceRestClient.getItem(input.getItemId()), ItemModel.class);
            StorageModel storage = conversionService.convert(storageServiceRestClient.getStorageByItem(input.getItemId()), StorageModel.class);

            return GetItemFromStorageResult
                    .builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .description(item.getDescription())
                    .vendor(item.getVendor())
                    .multimedia(item.getMultimedia())
                    .tags(item.getTags())
                    .price(storage.getPrice())
                    .quantity(storage.getQuantity())
                    .build();

        } catch (FeignException.ServiceUnavailable e) {
            throw new ServiceUnavailableException();
        } catch (FeignException.NotFound e) {
            throw new NotExistingItemException(input.getItemId());
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException();
        }
    }
}
