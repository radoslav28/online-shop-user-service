package com.onlineshop.user.core.processors.cart;

import com.onlineshop.storage.api.model.SaleModel;
import com.onlineshop.storage.api.operations.sales.create.CreateSalesInput;
import com.onlineshop.storage.restexport.StorageServiceRestClient;
import com.onlineshop.user.api.exceptions.DisallowedIdException;
import com.onlineshop.user.api.model.CartItemModel;
import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartInput;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartOperation;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartInput;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartOperation;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartInput;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartOperation;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartResult;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellCartCore implements SellCartOperation {
    private final UserRepository userRepository;
    private final GetCartOperation getCart;
    private final EmptyCartOperation emptyCart;
    private final StorageServiceRestClient storageServiceRestClient;
    private final ConversionService conversionService;
    @Override
    public SellCartResult process(SellCartInput input) {

        User user = userRepository
                .findById(UUID.fromString(input.getUserId()))
                .orElseThrow(() -> new DisallowedIdException(input.getUserId()));

        List<CartItemModel> cartItemList = getCart.process(GetCartInput
                        .builder()
                        .userId(input.getUserId())
                        .build())
                .getCartItems();

        Map<String, Long> items = cartItemList
                .stream()
                .collect(Collectors.toMap(ci -> ci.getItem().getId(), CartItemModel::getQuantity, (a, b) -> b));

        List<SaleModel> sales = storageServiceRestClient.createSales(CreateSalesInput
                        .builder()
                        .userId(String.valueOf(user.getId()))
                        .items(items)
                        .build())
                .getSales();

        emptyCart.process(EmptyCartInput
                .builder()
                .userId(input.getUserId())
                .build());

        return SellCartResult
                .builder()
                .user(conversionService.convert(user, UserModel.class))
                .sales(sales)
                .build();
    }
}
