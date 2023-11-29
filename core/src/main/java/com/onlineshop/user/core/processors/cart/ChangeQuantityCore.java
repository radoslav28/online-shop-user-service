package com.onlineshop.user.core.processors.cart;

import com.onlineshop.user.api.exceptions.InvalidQuantityException;
import com.onlineshop.user.api.exceptions.NotEnoughStockException;
import com.onlineshop.user.api.exceptions.NotExistingItemException;
import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import com.onlineshop.user.api.operations.cartitem.changequantity.ChangeQuantityInput;
import com.onlineshop.user.api.operations.cartitem.changequantity.ChangeQuantityOperation;
import com.onlineshop.user.api.operations.cartitem.changequantity.ChangeQuantityResult;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageInput;
import com.onlineshop.user.core.processors.item.GetItemFromStorageOperation;
import com.onlineshop.user.persistence.entities.CartItem;
import com.onlineshop.user.persistence.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeQuantityCore implements ChangeQuantityOperation {

    private final CartItemRepository cartItemRepository;
    private final GetItemFromStorageOperation getItemFromStorage;
    private final ConversionService conversionService;

    @Override
    public ChangeQuantityResult process(ChangeQuantityInput input) {

        try {
            ItemFromStorageModel itemFromStorage = itemFromStorage(input.getItemId());

            CartItem cartItem = cartItemRepository
                    .findByUserIdAndItemId(UUID.fromString(input.getUserId()), UUID.fromString(input.getItemId()))
                    .orElseThrow(() -> new  NotExistingItemException(input.getItemId()));

            cartItem.setQuantity(input.getQuantity());
            cartItem.setPrice(changePrice(itemFromStorage, input.getQuantity()));

            cartItemRepository.save(cartItem);

            return ChangeQuantityResult.builder().build();
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }

    private ItemFromStorageModel itemFromStorage (String itemId) {
        return conversionService.convert(getItemFromStorage.process(GetItemFromStorageInput
                .builder()
                .itemId(itemId)
                .build()), ItemFromStorageModel.class);

    }

    private BigDecimal changePrice (ItemFromStorageModel itemFromStorage, Long quantity) {

        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if (quantity > itemFromStorage.getQuantity()) {
            throw new NotEnoughStockException();
        }

        return itemFromStorage
                .getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}
