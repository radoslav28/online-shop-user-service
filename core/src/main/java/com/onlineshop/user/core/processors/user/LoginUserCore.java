package com.onlineshop.user.core.processors.user;

import com.onlineshop.user.api.exceptions.WrongCredentialsException;
import com.onlineshop.user.api.operations.user.loginuser.LoginUserInput;
import com.onlineshop.user.api.operations.user.loginuser.LoginUserOperation;
import com.onlineshop.user.api.operations.user.loginuser.LoginUserResult;
import com.onlineshop.user.core.security.CustomUserSec;
import com.onlineshop.user.core.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserCore implements LoginUserOperation {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public LoginUserResult process(LoginUserInput input) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()));
        } catch (BadCredentialsException e){
            throw new WrongCredentialsException();
        }

        final CustomUserSec userDetails = (CustomUserSec) userDetailsService.loadUserByUsername(input.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return LoginUserResult.builder().jwt(jwt).build();
    }
}

