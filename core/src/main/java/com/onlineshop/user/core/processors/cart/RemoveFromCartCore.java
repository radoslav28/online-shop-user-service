package com.onlineshop.user.core.processors.cart;

import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.api.operations.cartitem.removefromcart.RemoveFromCartInput;
import com.onlineshop.user.api.operations.cartitem.removefromcart.RemoveFromCartOperation;
import com.onlineshop.user.api.operations.cartitem.removefromcart.RemoveFromCartResult;
import com.onlineshop.user.persistence.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemoveFromCartCore implements RemoveFromCartOperation {

    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public RemoveFromCartResult process(RemoveFromCartInput input) {

        try {
            cartItemRepository.deleteByUserIdAndItemId(UUID.fromString(input.getUserId()), UUID.fromString(input.getItemId()));

            return RemoveFromCartResult.builder().build();
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }
}
