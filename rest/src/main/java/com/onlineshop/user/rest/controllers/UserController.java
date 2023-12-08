package com.onlineshop.user.rest.controllers;

import com.onlineshop.user.api.exceptions.IdParseException;
import com.onlineshop.user.api.operations.user.getsalesbyuser.GetSalesByUserInput;
import com.onlineshop.user.api.operations.user.getsalesbyuser.GetSalesByUserOperation;
import com.onlineshop.user.api.operations.user.loginuser.LoginUserInput;
import com.onlineshop.user.api.operations.user.loginuser.LoginUserOperation;
import com.onlineshop.user.api.operations.user.registeruser.RegisterUserInput;
import com.onlineshop.user.api.operations.user.registeruser.RegisterUserOperation;
import com.onlineshop.user.core.security.jwt.UserTokenManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class UserController {
    private final RegisterUserOperation registerUser;
    private final LoginUserOperation loginUser;
    private final GetSalesByUserOperation getSalesByUser;
    private final UserTokenManager userTokenManager;

    @Operation(summary = "Register user", description = "Save user")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterUserInput input) {

        return new ResponseEntity<>(registerUser.process(input), HttpStatus.CREATED);
    }

    @Operation(summary = "Login user", description = "Generate auth token")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginUserInput loginUserInput) {

        return new ResponseEntity<>(loginUser.process(loginUserInput).getJwt(), HttpStatus.OK);
    }

    @Operation(summary = "Get sales", description = "Get sales for current user")
    @GetMapping("/sales")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getSalesByUser (@RequestHeader("Authorization") String token) {

        String id;
        try {
            id = userTokenManager.getIdFromToken(token);
        } catch (ParseException e) {
            throw new IdParseException();
        }

        GetSalesByUserInput input = GetSalesByUserInput
                .builder()
                .userId(id)
                .build();

        return new ResponseEntity<>(getSalesByUser.process(input), HttpStatus.OK);
    }

}

