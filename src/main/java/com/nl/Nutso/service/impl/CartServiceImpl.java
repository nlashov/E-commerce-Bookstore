package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.repository.BookRepository;
import com.nl.Nutso.repository.CartItemRepository;
import com.nl.Nutso.repository.CartRepository;
import com.nl.Nutso.repository.UserRepository;
import com.nl.Nutso.service.CartService;
import com.nl.Nutso.service.exception.BookAlreadyInCartException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

        if (isBookAlreadyInAnyCart(book)) {
            throw new BookAlreadyInCartException("Book is already in another user's cart");
        }

        CartItemEntity cartItem = findCartItem(cartItems, book.getId());

        if (cartItem == null) {
            cartItem = new CartItemEntity();
            cartItem.setBook(book);
            cartItem.setPrice(book.getPrice());
            cartItem.setCart(cart);
            cartItems.add(cartItem);

            book.setAvailable(false);
            bookRepository.save(book);

        } else {
            cartItem.setPrice(book.getPrice());
        }

        scheduleCartExpiryTask(cart);

        cartItemRepository.save(cartItem);

        cart.setCartItems(cartItems);
        cart.setTotalItems(calculateTotalItems(cartItems));
        cart.setTotalPrice(calculateTotalPrice(cartItems));
        cart.setUser(user);

        return cartRepository.save(cart);
    }


    @Override
    @Transactional
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
            book.setAvailable(true);
            bookRepository.save(book);
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

    @Override
    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Set<CartItemEntity> getCartItemsForUser(UserEntity user) {
        CartEntity cart = user.getCart();

        if (cart != null) {
            return cart.getCartItems();
        } else {
            cart = new CartEntity();
            user.setCart(cart);
            userRepository.save(user);
            return Collections.emptySet();
        }
    }

    private CartItemEntity findCartItem(Set<CartItemEntity> cartItems, Long bookId) {
        return cartItems.stream()
                .filter(item -> Objects.equals(item.getBook().getId(), bookId))
                .findFirst()
                .orElse(null);
    }



    public boolean isBookAlreadyInAnyCart(BookEntity book) {
        List<UserEntity> allUsers = userRepository.findAll();

        for (UserEntity user : allUsers) {
            CartEntity cart = user.getCart();
            if (cart != null && isBookInCart(cart, book)) {
                return true; // Book is found in at least one cart
            }
        }

        return false; // Book is not in any cart
    }



    private boolean isBookInCart(CartEntity cart, BookEntity book) {
        Set<CartItemEntity> cartItems = cart.getCartItems();

        if (cartItems != null) {
            for (CartItemEntity cartItem : cartItems) {
                if (cartItem.getBook().equals(book)) {
                    return true;
                }
            }
        }
        return false;
    }

    private double calculateTotalPrice(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToDouble(CartItemEntity::getPrice)
                .sum();
    }

    public int calculateTotalItems(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }


    @Transactional
    private void scheduleCartExpiryTask(CartEntity cart) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.schedule(() -> {
            try {

                CartEntity cartWithItems = cartRepository.findByIdWithItems(cart.getId()).orElse(null);

                if (cartWithItems != null) {

                    Set<CartItemEntity> cartItems = cartWithItems.getCartItems();
                    if (cartItems != null) {
                        cartItems.forEach(cartItem -> {
                            BookEntity book = cartItem.getBook();
                            book.setAvailable(true);
                            bookRepository.save(book);
                        });
                    }

                    cartRepository.delete(cartWithItems);

                    // Log after saving changes to the cart
                    System.out.println("Cart deleted: " + cartWithItems.getId());
                }
            } catch (Exception e) {
                // TODO Log any exceptions that might occur during the task execution
                e.printStackTrace();
            }
            //TODO test what happens after an order is placed
        }, 2, TimeUnit.MINUTES);

        scheduler.shutdown();
    }
}
