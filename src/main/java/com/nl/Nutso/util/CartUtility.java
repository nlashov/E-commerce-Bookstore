package com.nl.Nutso.util;

import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("cartUtility")
public class CartUtility {
    private final CartService cartService;

    @Autowired
    public CartUtility(CartService cartService) {
        this.cartService = cartService;
    }

    public int getCartItemCount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserEntity user) {
            Set<CartItemEntity> cartItems = cartService.getCartItemsForUser(user);
            if (cartItems != null) {
                System.out.println("Cart items: " + cartItems); // Add this line
                return cartService.calculateTotalItems(cartItems);
            }
            else {
                System.out.println("cartItems is null");
            }
        }


        return 0;
    }
}
