package com.onlineshop.user.rest.controllers;

import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageInput;
import com.onlineshop.user.api.operations.item.getitemsfromstorage.GetItemFromStorageOperation;
import com.onlineshop.user.api.operations.item.getitemsfromstorages.GetItemsFromStoragesInput;
import com.onlineshop.user.api.operations.item.getitemsfromstorages.GetItemsFromStoragesOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@Tag(name = "Item Controller")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final GetItemFromStorageOperation getItemFromStorage;
    private final GetItemsFromStoragesOperation getItemsFromStorages;

    @Operation(summary = "Get items from storage", description = "Return item's titles")
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getItemFromStorage(@RequestParam @UUID @NotBlank String itemId) {

        GetItemFromStorageInput input = GetItemFromStorageInput
                .builder()
                .itemId(itemId)
                .build();

        return new ResponseEntity<>(getItemFromStorage.process(input), HttpStatus.OK);
    }

    @Operation(summary = "Get items from storage", description = "Return item's titles")
    @GetMapping("/items")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getItemsFromStorages(@RequestParam List<@UUID @NotBlank String> itemIds) {

        GetItemsFromStoragesInput input = GetItemsFromStoragesInput
                .builder()
                .itemIds(itemIds)
                .build();

        return new ResponseEntity<>(getItemsFromStorages.process(input), HttpStatus.OK);
    }
}
