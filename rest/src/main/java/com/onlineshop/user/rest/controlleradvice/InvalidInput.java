package com.onlineshop.user.rest.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InvalidInput {

    private List<String> errors;

    public InvalidInput() {
        this.errors = new ArrayList<>();
    }

    public void addMessage(String message) {
        this.errors.add(message);
    }

    @Override
    public String toString() {
        return "errors:" + errors;
    }
}