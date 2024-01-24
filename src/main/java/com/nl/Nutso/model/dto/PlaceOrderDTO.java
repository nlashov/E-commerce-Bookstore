package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.PaymentMethodEnum;
import jakarta.validation.constraints.NotNull;

public record PlaceOrderDTO(@NotNull String street,
                            @NotNull String city,
                            String region,
                            String zipCode,
                            @NotNull String phoneNumber,
                            String note,
                            PaymentMethodEnum paymentMethod) {

}
