package com.onlineshop.user.core.processors.cart;

import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartInput;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartOperation;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartResult;
import com.onlineshop.user.persistence.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmptyCartCore implements EmptyCartOperation{

    private final CartItemRepository cartItemRepository;

    @Override
    public EmptyCartResult process(EmptyCartInput input) {

        try {
            cartItemRepository.deleteByUserId(UUID.fromString(input.getUserId()));

            return EmptyCartResult.builder().build();
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }
}
