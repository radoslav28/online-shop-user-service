package com.onlineshop.user.core.processors.cart;

import com.onlineshop.store.api.model.ItemModel;
import com.onlineshop.store.restexport.StoreServiceRestClient;
import com.onlineshop.user.api.exceptions.DisallowedIdException;
import com.onlineshop.user.api.exceptions.NotExistingItemException;
import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.api.model.CartItemModel;
import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartInput;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartOperation;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartResult;
import com.onlineshop.user.persistence.entities.CartItem;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.CartItemRepository;
import com.onlineshop.user.persistence.repositories.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCartCore implements GetCartOperation {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final StoreServiceRestClient storeServiceRestClient;
    private final ConversionService conversionService;

    @Override
    public GetCartResult process(GetCartInput input) {

        UserModel user = getUser(input.getUserId());

        List<CartItem> cartItemList;
        try {
            cartItemList = cartItemRepository.findByUserId(UUID.fromString(input.getUserId()));
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }

        List<ItemModel> items = getItems(cartItemList);

        List<CartItemModel> cartItems = new ArrayList<>();
        cartItemList.forEach(ci -> {
            CartItemModel cartItem = conversionService.convert(ci, CartItemModel.class);

            items
                   .stream()
                   .filter(i -> String.valueOf(ci.getItemId()).equals(i.getId()))
                   .findFirst()
                   .ifPresent(i -> cartItem.setItem(i));

           cartItems.add(cartItem);
        });

        return GetCartResult
                .builder()
                .user(user)
                .cartItems(cartItems)
                .build();
    }

    private UserModel getUser (String userId) {
        try {

            User user = userRepository.
                    findById(UUID.fromString(userId))
                    .orElseThrow(() -> new  DisallowedIdException(userId));

            return conversionService.convert(user, UserModel.class);

        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }

    private List<String> getItemsIds (List<CartItem> cartItems) {
        return cartItems
                .stream()
                .map(ci -> String.valueOf(ci.getItemId()))
                .toList();
    }

    private List<ItemModel> getItems (List<CartItem> cartItems) {

        try {

            List<String> itemsIds = getItemsIds(cartItems);

            return storeServiceRestClient
                    .getItemsByIds(itemsIds)
                    .getItemModels();

        } catch (FeignException.ServiceUnavailable e) {
            throw new ServiceUnavailableException();
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException();
        } catch (FeignException.NotFound e) {
            throw new NotExistingItemException("this");
        }
    }
}
