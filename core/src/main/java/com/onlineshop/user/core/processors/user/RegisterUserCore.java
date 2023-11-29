package com.onlineshop.user.core.processors.user;

import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.api.operations.user.registeruser.RegisterUserInput;
import com.onlineshop.user.api.operations.user.registeruser.RegisterUserOperation;
import com.onlineshop.user.api.operations.user.registeruser.RegisterUserResult;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserCore implements RegisterUserOperation {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    @Override
    public RegisterUserResult process(RegisterUserInput input) {

        try {
            User user = conversionService.convert(input, User.class);

            User persisted = userRepository.save(user);
            return RegisterUserResult
                    .builder()
                    .id(String.valueOf(persisted.getId()))
                    .build();
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }
}
