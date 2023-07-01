package com.debuggeando_ideas.best_travel.util.enums;

public enum Tables {
    CUSTOMERS("customers"),
    FLIES("flights"),
    RESERVATIONS("reservations"),
    TICKETS("tickets"),
    TOURS("tours"),
    HOTELS("hotels");

    private final String tableName;

    Tables(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
