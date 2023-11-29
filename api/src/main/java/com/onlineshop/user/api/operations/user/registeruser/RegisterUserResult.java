package com.onlineshop.user.api.operations.user.registeruser;

import com.onlineshop.user.api.base.ProcessorResult;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserResult implements ProcessorResult {
    private String id;
}
