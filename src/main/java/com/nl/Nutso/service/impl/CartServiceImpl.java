package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.repository.CartItemRepository;
import com.nl.Nutso.repository.CartRepository;
import com.nl.Nutso.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    @Transactional
    public CartEntity addItemToCart(UserEntity user, BookEntity book) {
        CartEntity cart = user.getCart();

        if (cart == null) {
            cart = new CartEntity();
        }

        Set<CartItemEntity> cartItems = cart.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<>();
            cart.setCartItems(cartItems);
        }

        CartItemEntity cartItem = findCartItem(cartItems, book.getId());

        if (cartItem == null) {
            cartItem = new CartItemEntity();
            cartItem.setBook(book);
            cartItem.setPrice(book.getPrice());
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        } else {
            cartItem.setPrice(book.getPrice());
        }

        cartItemRepository.save(cartItem);

        cart.setCartItems(cartItems);
        double totalPrice = calculateTotalPrice(cartItems);
        int totalItems = calculateTotalItems(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart() {

    }

    private CartItemEntity findCartItem(Set<CartItemEntity> cartItems, Long bookId) {
        return cartItems.stream()
                .filter(item -> Objects.equals(item.getBook().getId(), bookId))
                .findFirst()
                .orElse(null);
    }

    private double calculateTotalPrice(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToDouble(CartItemEntity::getPrice)
                .sum();
    }

    private int calculateTotalItems(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }


}
