package com.onlineshop.user.core.security;

import com.onlineshop.user.api.exceptions.NotExistingUserException;
import com.onlineshop.user.api.exceptions.ServiceUnavailableException;
import com.onlineshop.user.persistence.entities.User;
import com.onlineshop.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new  NotExistingUserException(email));

            return new CustomUserSec(user.getEmail(),
                    user.getPassword(),
                    user.getId().toString(),
                    String.valueOf(user.getRole()),
                    new ArrayList<>());
        } catch (JDBCConnectionException e) {
            throw new ServiceUnavailableException();
        }
    }
}
