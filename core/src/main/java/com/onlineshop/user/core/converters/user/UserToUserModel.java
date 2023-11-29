package com.onlineshop.user.core.converters.user;

import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.persistence.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserModel implements Converter<User, UserModel> {
    @Override
    public UserModel convert(User source) {
        return UserModel
                .builder()
                .id(String.valueOf(source.getId()))
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phone(source.getPhone())
                .build();
    }
}
