package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.repository.CartItemRepository;
import com.nl.Nutso.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void addItemToCart(UserEntity user, BookEntity book) {
// Check if the book is available
//        if (!book.isAvailable()) {
//            // Handle the case when the book is not available (e.g., show a message to the user)
//            return;
//        }

        CartEntity cart = user.getCart();

        if (cart == null) {
            cart = new CartEntity();
        }
        Set<CartItemEntity> cartItems = cart.getCartItems();
        CartItemEntity cartItem = findCartItem(cartItems, book.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItemEntity();
                cartItem.setBook(book);
                cartItem.setPrice(book.getPrice());
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + ( quantity * product.getCostPrice()));
                itemRepository.save(cartItem);
            }
    }

    @Override
    public void removeItemFromCart() {

    }

