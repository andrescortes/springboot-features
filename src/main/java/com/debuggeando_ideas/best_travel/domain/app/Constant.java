package com.debuggeando_ideas.best_travel.domain.app;

import java.math.BigDecimal;

public class Constant {

    public static final String ERROR_FLY_NOT_FOUND = "Fly not found";
    public static final String ERROR_CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String ERROR_TICKET_NOT_FOUND = "Ticket not found";
    public static final BigDecimal CHARGE_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);
    public static final BigDecimal CHARGE_PRICE_PERCENTAGE_RESERVATION = BigDecimal.valueOf(0.20);
    public static final String TABLE_RESERVATION = "reservation";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_HOTEL = "hotel";

    private Constant() {

    }

}
