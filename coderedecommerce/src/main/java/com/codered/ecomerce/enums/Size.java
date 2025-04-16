package com.codered.ecomerce.enums;

import java.lang.AutoCloseable;

public enum Size implements AutoCloseable {
    M,
    S,
    L,
    XL,
    XS,
    XM,
    XXL,
    XXS,
    XXM,
    XXXL,
    XXXS,
    XXXM;

    public void close(){}
}
