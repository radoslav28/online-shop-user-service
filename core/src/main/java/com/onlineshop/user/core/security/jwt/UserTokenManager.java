package com.onlineshop.user.core.security.jwt;

import com.onlineshop.user.api.exceptions.AccessDeniedException;
import com.onlineshop.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserTokenManager {
    private final UserRepository userRepository;

    public UUID getIdFromToken(String token) throws org.apache.tomcat.util.json.ParseException {
        String bodyEncoded = token.split("\\.")[1];
        String payloadAsString = new String(Base64.getUrlDecoder().decode(bodyEncoded));

        Map<String, Object> payload = null;
        try {
            payload = new JSONParser(payloadAsString).parseObject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String id = (String) payload.get("id");

        return UUID.fromString(id);

    }

    public String getRoleFromToken(String token) throws org.apache.tomcat.util.json.ParseException {
        String bodyEncoded = token.split("\\.")[1];
        String payloadAsString = new String(Base64.getUrlDecoder().decode(bodyEncoded));

        Map<String, Object> payload = null;
        try {
            payload = new JSONParser(payloadAsString).parseObject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String role = (String) payload.get("role");

        return role;

    }

    public void checkAdminAccess (String token) throws org.apache.tomcat.util.json.ParseException {
        String userRole = getRoleFromToken(token);
        if (!userRole.equals("ADMIN")) {
            throw new AccessDeniedException();
        }
    }
}
