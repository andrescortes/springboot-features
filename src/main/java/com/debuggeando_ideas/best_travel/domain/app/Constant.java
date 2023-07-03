package com.debuggeando_ideas.best_travel.domain.app;

import java.math.BigDecimal;

public class Constant {

    public static final BigDecimal CHARGE_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);
    public static final BigDecimal CHARGE_PRICE_PERCENTAGE_RESERVATION = BigDecimal.valueOf(0.20);

    public static final String SHEET_NAME = "Customer total sales";
    public static final String FONT_TYPE = "Arial";
    public static final String COLUMN_CUSTOMER_ID = "id";
    public static final String COLUMN_CUSTOMER_NAME = "name";
    public static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
    public static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    public static final String REPORTS_PATH = "reports";
    public static final String FILE_TYPE = ".xlsx";
    public static final String FILE_NAME = "SALES-%s.xlsx";

    private Constant() {

    }
}
