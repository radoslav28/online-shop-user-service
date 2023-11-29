package com.onlineshop.user.rest.controlleradvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ErrorPrinter {

    private final ObjectMapper objectMapper;

    public String getErrorList(InvalidInput errorList) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorList);
        } catch (JsonProcessingException ex) {
            return errorList.toString();
        }
    }

    public String mapErrorToMessage(ObjectError objectError) {
        String defaultMessage = objectError.getDefaultMessage();
        String objectErrorMessage = Objects
                .requireNonNull(objectError.getCodes())[1]
                .replace("NotEmpty.", "");

        int splitPosition = objectErrorMessage.indexOf('.') + 1;
        String field = objectErrorMessage.substring(splitPosition);

        return field + " " + defaultMessage;
    }
}
