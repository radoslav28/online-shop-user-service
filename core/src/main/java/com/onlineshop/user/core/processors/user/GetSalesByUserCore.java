package com.onlineshop.user.core.processors.user;

import com.onlineshop.storage.api.exceptions.ServiceUnavailableException;
import com.onlineshop.storage.api.model.SaleModel;
import com.onlineshop.storage.restexport.StorageServiceRestClient;
import com.onlineshop.user.api.exceptions.DisallowedIdException;
import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.api.operations.user.getsalesbyuser.GetSalesByUserInput;
import com.onlineshop.user.api.operations.user.getsalesbyuser.GetSalesByUserOperation;
import com.onlineshop.user.api.operations.user.getsalesbyuser.GetSalesByUserResult;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSalesByUserCore implements GetSalesByUserOperation {

    private final UserRepository userRepository;
    private final StorageServiceRestClient storageServiceRestClient;
    private final ConversionService conversionService;

    @Override
    public GetSalesByUserResult process(GetSalesByUserInput input) {

        try {

            User user = userRepository
                    .findById(UUID.fromString(input.getUserId()))
                    .orElseThrow(() -> new DisallowedIdException(input.getUserId()));

            List<SaleModel> sales = storageServiceRestClient
                    .getSalesByUser(input.getUserId())
                    .getSales();

            return GetSalesByUserResult
                    .builder()
                    .user(conversionService.convert(user, UserModel.class))
                    .sales(sales)
                    .build();

        } catch (JDBCConnectionException | FeignException.ServiceUnavailable e) {
            throw new ServiceUnavailableException();
        }
    }
}
