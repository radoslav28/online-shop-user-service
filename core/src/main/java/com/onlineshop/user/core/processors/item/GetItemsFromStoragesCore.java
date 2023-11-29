package com.onlineshop.user.core.processors.item;

import com.onlineshop.storage.api.model.StorageModel;
import com.onlineshop.storage.restexport.StorageServiceRestClient;
import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.store.restexport.StoreServiceRestClient;
import com.onlineshop.user.api.exceptions.*;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import com.onlineshop.user.api.operations.item.getitemsfromstorages.GetItemsFromStoragesInput;
import com.onlineshop.user.api.operations.item.getitemsfromstorages.GetItemsFromStoragesOperation;
import com.onlineshop.user.api.operations.item.getitemsfromstorages.GetItemsFromStoragesResult;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetItemsFromStoragesCore implements GetItemsFromStoragesOperation {

    private final StoreServiceRestClient storeServiceRestClient;
    private final StorageServiceRestClient storageServiceRestClient;
    private final ConversionService conversionService;

    @Override
    public GetItemsFromStoragesResult process(GetItemsFromStoragesInput input) {
        try {

            List<ItemModel> items = storeServiceRestClient.getItemsByIds(input.getItemIds()).getItemModels();
            List<StorageModel> storages = storageServiceRestClient.getStoragesByItems(input.getItemIds()).getStorages();

            List<ItemFromStorageModel> itemsFromStorages = items
                    .stream()
                    .map(i -> conversionService.convert(i, ItemFromStorageModel.class))
                    .toList();

            itemsFromStorages
                    .forEach(i -> {
                        StorageModel storage = storages
                                .stream()
                                .filter(s -> s.getItemId().equals(i.getId()))
                                .findFirst()
                                .orElseThrow(() -> new NotExistingItemException(i.getId()));

                        i.setPrice(storage.getPrice());
                        i.setQuantity(storage.getQuantity());
                    });

            return GetItemsFromStoragesResult
                    .builder()
                    .itemsFromStorage(itemsFromStorages)
                    .build();

        } catch (FeignException.ServiceUnavailable e) {
            throw new ServiceUnavailableException();
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException();
        }
    }
}
