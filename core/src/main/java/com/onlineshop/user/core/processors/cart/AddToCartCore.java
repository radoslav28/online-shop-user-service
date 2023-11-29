package com.onlineshop.user.core.processors.cart;

import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.user.api.exceptions.*;
import com.onlineshop.user.api.model.ItemFromStorageModel;
import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartInput;
import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartOperation;
import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartResult;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageInput;
import com.onlineshop.user.core.processors.item.GetItemFromStorageOperation;
import com.onlineshop.user.persistence.entities.CartItem;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.CartItemRepository;
import com.onlineshop.user.persistence.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddToCartCore implements AddToCartOperation {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final GetItemFromStorageOperation getItemFromStorage;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public AddToCartResult process(AddToCartInput input) {

        try {

            ItemFromStorageModel itemFromStorage = itemFromStorage(input.getItemId());

            CartItem cartItem = conversionService.convert(input, CartItem.class);
            cartItem.setPrice(calculatePriceByQuantity(itemFromStorage, cartItem.getQuantity()));
            CartItem persisted = cartItemRepository.save(cartItem);

            return AddToCartResult
                    .builder()
                    .id(String.valueOf(persisted.getId()))
                    .user(getUser(input.getUserId()))
                    .item(conversionService.convert(itemFromStorage, ItemModel.class))
                    .price(persisted.getPrice())
                    .quantity(persisted.getQuantity())
                    .build();

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

    private BigDecimal calculatePriceByQuantity (ItemFromStorageModel itemFromStorage, Long quantity) {

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

    private UserModel getUser (String userId) {

        User user = userRepository
                .findById(UUID.fromString(userId))
                .orElseThrow(() -> new DisallowedIdException(userId));

        return conversionService.convert(user, UserModel.class);
    }
}
