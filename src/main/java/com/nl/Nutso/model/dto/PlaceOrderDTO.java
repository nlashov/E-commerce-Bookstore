package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.PaymentMethodEnum;

public record PlaceOrderDTO(String street,
                            String city,
                            String region,
                            String zipCode,
                            String note,
                            PaymentMethodEnum paymentMethod) {


}
