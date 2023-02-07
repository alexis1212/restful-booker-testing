package com.aleksandrab.restfulBookerTest.helpers;

/**
 * Custom Assertions class for the most common checks
 */
public class Assertions {

    /**
     * Assert object of any type is not null or empty
     * @param object object of any type (used String, Integer)
     * @return boolean true if Object is not empty, otherwise false
     */
    public static boolean notNull(Object object) {
        return object != null && !object.equals("") && !object.equals(0);
    }
}
