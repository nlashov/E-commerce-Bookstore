package com.nl.Nutso.service;


import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.UserEntity;

public interface CartService {

    CartEntity addItemToCart(UserEntity user, BookEntity book);
    CartEntity removeItemFromCart(UserEntity user, BookEntity book);
    CartEntity updateCart(UserEntity user);

}
