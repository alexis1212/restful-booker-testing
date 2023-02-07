package com.aleksandrab.restfulBookerTest.dtos;

/**
 * Enum containing all possible endpoint URLs
 */
public enum EndpointUri {
    BOOKINGS("/booking"),
    BOOKING_DETAILS("/booking/{id}"),
    AUTHENTICATE("/auth");

    private final String uri;

    EndpointUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
