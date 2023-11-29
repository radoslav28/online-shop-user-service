package com.onlineshop.user.core.converters.user;

import com.onlineshop.user.api.operations.user.registeruser.RegisterUserInput;
import com.onlineshop.user.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserInputToUser implements Converter<RegisterUserInput, User> {

    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public User convert(RegisterUserInput source) {
        return User
                .builder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phone(source.getPhone())
                .build();
    }
}
