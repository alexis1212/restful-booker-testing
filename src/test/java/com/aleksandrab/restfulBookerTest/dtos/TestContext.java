package com.aleksandrab.restfulBookerTest.dtos;

import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import io.restassured.response.Response;

/**
 * Data transfer object to store test context can be used and verified in different steps/glue files
 */
//@Data
public class TestContext {
    private Response storedResponse;
    private String token;
    private Booking booking;

    public Response getStoredResponse() {
        return storedResponse;
    }

    public void setStoredResponse(Response storedResponse) {
        this.storedResponse = storedResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
