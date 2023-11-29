package com.onlineshop.user.api.operations.user.loginuser;

import com.onlineshop.user.api.base.ProcessorInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInput implements ProcessorInput {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
