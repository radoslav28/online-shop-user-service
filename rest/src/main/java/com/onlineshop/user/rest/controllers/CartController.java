package com.onlineshop.user.rest.controllers;

import com.onlineshop.user.api.exceptions.IdParseException;
import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartInput;
import com.onlineshop.user.api.operations.cartitem.addtocart.AddToCartOperation;
import com.onlineshop.user.api.operations.cartitem.changequantity.ChangeQuantityInput;
import com.onlineshop.user.api.operations.cartitem.changequantity.ChangeQuantityOperation;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartInput;
import com.onlineshop.user.api.operations.cartitem.emptycart.EmptyCartOperation;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartInput;
import com.onlineshop.user.api.operations.cartitem.getcart.GetCartOperation;
import com.onlineshop.user.api.operations.cartitem.getcartprice.GetCartPriceInput;
import com.onlineshop.user.api.operations.cartitem.getcartprice.GetCartPriceOperation;
import com.onlineshop.user.api.operations.cartitem.removefromcart.RemoveFromCartInput;
import com.onlineshop.user.api.operations.cartitem.removefromcart.RemoveFromCartOperation;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartInput;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartOperation;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartResult;
import com.onlineshop.user.core.security.jwt.UserTokenManager;
import com.onlineshop.user.kafka.KafkaGateway;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart Controller")
@RequiredArgsConstructor
@Validated
public class CartController {
    private final UserTokenManager userTokenManager;
    private final KafkaGateway kafkaGateway;
    private final AddToCartOperation addToCart;
    private final RemoveFromCartOperation removeFromCart;
    private final EmptyCartOperation emptyCart;
    private final GetCartOperation getCart;
    private final GetCartPriceOperation getCartPrice;
    private final ChangeQuantityOperation changeQuantity;
    private final SellCartOperation sellCart;

    @Operation(summary = "Add to cart", description = "Add item to shopping cart")
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> addToCart(@RequestBody @Valid AddToCartInput input,
                                       @RequestHeader("Authorization") String token) {

        try {
            input.setUserId(userTokenManager.getIdFromToken(token));
        } catch (ParseException e) {
            throw new IdParseException();
        }

        return new ResponseEntity<>(addToCart.process(input), HttpStatus.CREATED);
    }

    @Operation(summary = "Remove from cart", description = "Remove item from shopping cart")
    @DeleteMapping("/item")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> removeFromCart (@RequestBody @Valid RemoveFromCartInput input,
                                             @RequestHeader("Authorization") String token) {
        try {
            input.setUserId(userTokenManager.getIdFromToken(token));
        } catch (ParseException e) {
            throw new IdParseException();
        }

        return new ResponseEntity<>(removeFromCart.process(input), HttpStatus.OK);
    }

    @Operation(summary = "Empty cart", description = "Remove all items from shopping cart")
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> emptyCart (@RequestBody @Valid EmptyCartInput input,
                                        @RequestHeader("Authorization") String token) {
        try {
            input.setUserId(userTokenManager.getIdFromToken(token));
        } catch (ParseException e) {
            throw new IdParseException();
        }

        return new ResponseEntity<>(emptyCart.process(input), HttpStatus.OK);
    }

    @Operation(summary = "Get cart", description = "Get all items from shopping cart")
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getCart (@RequestHeader("Authorization") String token) {

        String id;
        try {
            id = userTokenManager.getIdFromToken(token);
        } catch (ParseException e) {
            throw new IdParseException();
        }

        GetCartInput input = GetCartInput
                .builder()
                .userId(id)
                .build();

        return new ResponseEntity<>(getCart.process(input), HttpStatus.OK);
    }

    @Operation(summary = "Change quantity", description = "Change quantity of item in cart")
    @PutMapping("/item")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> changeQuantity (@RequestBody @Valid ChangeQuantityInput input,
                                             @RequestHeader("Authorization") String token) {
        try {
            input.setUserId(userTokenManager.getIdFromToken(token));
        } catch (ParseException e) {
            throw new IdParseException();
        }

        return new ResponseEntity<>(changeQuantity.process(input), HttpStatus.CREATED);
    }

    @Operation(summary = "Get full price", description = "Get shopping cart's full price ")
    @GetMapping("/price")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getCartPrice (@RequestHeader("Authorization") String token) {

        String id;
        try {
            id = userTokenManager.getIdFromToken(token);
        } catch (ParseException e) {
            throw new IdParseException();
        }

        GetCartPriceInput input = GetCartPriceInput
                .builder()
                .userId(id)
                .build();

        return new ResponseEntity<>(getCartPrice.process(input), HttpStatus.OK);
    }

    @Operation(summary = "Sell cart", description = "Empty your cart and save an invoice for purchase")
    @DeleteMapping("/sell")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> sellCart (@RequestHeader("Authorization") String token) {

        String id;
        try {
            id = userTokenManager.getIdFromToken(token);
        } catch (ParseException e) {
            throw new IdParseException();
        }

        SellCartInput input = SellCartInput
                .builder()
                .userId(id)
                .build();

        SellCartResult result = sellCart.process(input);
        kafkaGateway.sendMessage(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
