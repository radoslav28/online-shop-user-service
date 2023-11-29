package com.onlineshop.user.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
