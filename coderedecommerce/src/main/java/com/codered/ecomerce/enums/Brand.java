package com.codered.ecomerce.enums;

import java.lang.AutoCloseable;

public enum Brand implements AutoCloseable{
    INIT("0"),
    SUBVERSE("SUBVERSE"),
    PSYPHI("PSYPHI"),
    EMPRESS("EMPRESS"),
    CODEX("CODEX"),
    OTA_KUN("OTA-KUN"),
    RIP_N_TONE("RIP N TONE"),
    AZURE_PILL("AZURE PILL"),
    SCARLET_PILL("SCARLET PILL"),
    JUST_UNDER_NORMAL("JUST UNDER NORMAL");

    public void close(){}

    private final String label;

    Brand(String label){
        this.label = label;
    }

    public static Brand fromLabel(String label) {
        for (Brand b : Brand.values()) {
            if (b.label.equalsIgnoreCase(label)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant with label: " + label);
    }

    public String getLabel(){
        return label;
    }
}
