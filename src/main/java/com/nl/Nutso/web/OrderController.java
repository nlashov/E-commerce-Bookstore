package com.nl.Nutso.web;

import com.nl.Nutso.model.dto.PlaceOrderDTO;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.OrderEntity;
import com.nl.Nutso.model.entity.UserEntity;
import com.nl.Nutso.model.enums.PaymentMethodEnum;
import com.nl.Nutso.service.OrderService;
import com.nl.Nutso.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;


    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @ModelAttribute("paymentMethods")
    public PaymentMethodEnum[] paymentMethods() {
        return PaymentMethodEnum.values();
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, Principal principal, PlaceOrderDTO placeOrderDTO) {

        String customerEmail = principal.getName();
        UserEntity customer = userService.getUserByEmail(customerEmail);
        CartEntity cart = customer.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("placeOrderDTO", placeOrderDTO);
        return "order/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(Principal principal,
                             PlaceOrderDTO placeOrderDTO,
                             Model model) {

        String customerEmail = principal.getName();
        UserEntity customer = userService.getUserByEmail(customerEmail);
        CartEntity cart = customer.getCart();
        model.addAttribute("cart", cart);

        // Assuming you have a method to place the order in your OrderService
        OrderEntity order = orderService.placeOrder(cart, placeOrderDTO);

        // You might want to pass the order details to the view for confirmation
        // model.addAttribute("order", order);'

        // Redirect to a confirmation page or any other page as needed
        return "redirect:/order-confirmation";
    }

    @GetMapping("/order-confirmation")
    public String confirmOrder() {

        return "order/order-confirmation";
    }

    @GetMapping("/admin/orders-to-accept")
    public String ordersToAccept(Model model) {

        List<OrderEntity> ordersToAccept = orderService.getAllOrdersToAccept();
        model.addAttribute("orders", ordersToAccept);
        return "order/orders-to-accept";
    }

    @GetMapping("/admin/orders-accepted")
    public String ordersAccepted(Model model) {

        List<OrderEntity> acceptedOrders = orderService.getAllAcceptedOrders();
        model.addAttribute("orders", acceptedOrders);
        return "order/orders-accepted";
    }

    @PostMapping("/accept-order")
    public String acceptOrder(@RequestParam Long orderId,
                              RedirectAttributes redirectAttributes) {
        orderService.acceptOrder(orderId);
        redirectAttributes.addFlashAttribute("successMessage", "Order accepted successfully");
        return "redirect:/admin/orders-to-accept";
    }

    @PostMapping("/decline-order")
    public String declineOrder(@RequestParam Long orderId,
                              RedirectAttributes redirectAttributes) {
        orderService.cancelOrder(orderId);
        redirectAttributes.addFlashAttribute("successMessage", "Order has been declined");
        return "redirect:/admin/orders-to-accept";
    }
}

