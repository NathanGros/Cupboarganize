package com.bignat.viktus;

public enum Unit {
    PIECE("pcs"),
    GRAM("g"),
    KILOGRAM("kg"),
    MILLILITER("ml"),
    LITER("L"),
    TABLESPOON("tbsp"),
    TEASPOON("tsp"),
    CUP("cup");

    public final String label;

    Unit(String label) {
        this.label = label;
    }
}