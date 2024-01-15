package com.nl.Nutso.web;

import com.nl.Nutso.model.entity.BookEntity;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.CartItemEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.service.BookService;
import com.nl.Nutso.service.CartService;
import com.nl.Nutso.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);


    private final BookService bookService;
    private final UserService userService;
    private final CartService cartService;

    public CartController(BookService bookService, UserService userService, CartService cartService) {
        this.bookService = bookService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession httpSession) {
        logger.info("Entering cart method.");
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        UserEntity customer = userService.getUserByEmail(username);

        if (customer == null) {
            return "redirect:/login";
        }

        CartEntity cart = customer.getCart();
//        if(cart == null){
//            model.addAttribute("check", "No items in your cart");
//            return "cart";
//        }

        httpSession.setAttribute("totalItems", cart.getTotalItems());
        model.addAttribute("subTotal", cart.getTotalPrice());
        model.addAttribute("cart", cart);
        logger.info("Exiting cart method.");
        return "cart";
    }


    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("uuid") UUID bookUuid,
            Principal principal,
            HttpServletRequest request){

        if(principal == null){
            return "redirect:/login";
        }
        BookEntity book = bookService.getBookByUuid(bookUuid);
        String userEmail = principal.getName();
        UserEntity customer = userService.getUserByEmail(userEmail);
        System.out.println(customer.getEmail());
        System.out.println(principal.getName());

        CartEntity cart = cartService.addItemToCart(customer, book);
        return "redirect:" + request.getHeader("Referer");
    }
}
