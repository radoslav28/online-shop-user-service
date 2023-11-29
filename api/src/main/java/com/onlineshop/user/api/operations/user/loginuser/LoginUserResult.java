package com.onlineshop.user.api.operations.user.loginuser;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoginUserResult implements ProcessorResult {
    private final String jwt;
}
