/**
 * CodeRed E-Commerce System
 * This {@code Material} enum defines the available materials for the products in the application
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/03/2025
 */
package com.codered.ecomerce.enums;

import java.lang.AutoCloseable;

/**
 * Enum representing various material options for products
 */

public enum Material implements AutoCloseable {
    LEATHER,
    COTTON,
    LINEN,
    POLYESTER,
    SILK,
    SUADE,
    QUARTEROID,
    PLASTIC,
    LED,
    URANIUM,
    WHITE_BOARD,
    AIR,
    PELT,
    HUMAN,
    SPANDEX,
    FLEECE,
    DENIM,
    CANVAS,
    VELVET,
    WOOL,
    FLANNEL;

    public void close(){}
}
