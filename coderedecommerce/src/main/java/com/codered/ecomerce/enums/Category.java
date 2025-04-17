package com.codered.ecomerce.enums;

import java.lang.AutoCloseable;

public enum Category implements AutoCloseable{
    INIT("0"),
    T_SHIRT("T SHIRT"),
    HOODIE("HOODIE"),
    JACKET("JACKET"),
    TANK_TOP("TANK TOP"),
    SWEATER("SWEATER"),
    SHIRT("SHIRT"),
    VEST("VEST"),
    LONG_SLEEVE("LONG SLEEVE"),
    PANTS("PANTS"),
    CROP_TOP("CROP TOP"),
    SUIT_JACKET("SUIT JACKET"),
    DRESS_SHIRT("DRESS SHIRT"),
    TROUSERS("TROWSERS"),
    BLAZER("BLAZER"),
    DRESS("DRESS"),
    BLOUSE("BLOUSE"),
    SKIRT("SKIRT"),
    GOWN("GOWN"),
    TUNIC("TUNIC"),
    CAPE("CAPE"),
    SWEATPANTS("SWEATPANTS"),
    YUKATA("YUKATA"),
    FLANNEL("FLANNEL"),
    TURTLENECK("TURTLE NECK"),
    SHORTS("SHORTS"),
    TOP("TOP"); 

    private final String label;

    Category(String label){
        this.label = label;
    }

    public static Category fromLabel(String label) {
        for (Category c : Category.values()) {
            if (c.label.equalsIgnoreCase(label)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No enum constant with label: " + label);
    }

    public String getLabel(){
        return label;
    }

    public void close(){}

}
