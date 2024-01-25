package com.nl.Nutso.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error/already-in-cart")
    public String alreadyInCartErrorPage() {
        return "error/already-in-cart";
    }
}
