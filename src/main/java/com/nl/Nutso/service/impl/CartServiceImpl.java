package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.repository.CartItemRepository;
import com.nl.Nutso.repository.CartRepository;
import com.nl.Nutso.service.CartService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

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
        //TODO if book is already in the cart, don't add.
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartEntity removeItemFromCart(UserEntity user, BookEntity book) {

        CartEntity cart = user.getCart();

        if (cart == null) {
            cart = new CartEntity();
            user.setCart(cart);
        }

        Set<CartItemEntity> cartItems = cart.getCartItems();

        if (cartItems == null) {
            cartItems = new HashSet<>();
            cart.setCartItems(cartItems);
        }

        CartItemEntity cartItemToRemove = findCartItem(cartItems, book.getId());

        if (cartItemToRemove != null) {
            cartItems.remove(cartItemToRemove);
            cartItemRepository.delete(cartItemToRemove);
        }

        return cart;
    }

    public CartEntity updateCart(UserEntity user) {

        CartEntity cart = user.getCart();
        if (cart != null) {
            Set<CartItemEntity> cartItems = cart.getCartItems();
            if (cartItems != null) {
                cart.setTotalPrice(calculateTotalPrice(cartItems));
                cart.setTotalItems(calculateTotalItems(cartItems));
                return cartRepository.save(cart);
            }
        }
        return cart;
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
