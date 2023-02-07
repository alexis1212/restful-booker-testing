package com.aleksandrab.restfulBookerTest.requestcontext;

import com.aleksandrab.restfulBookerTest.dtos.EndpointUri;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aleksandrab.restfulBookerTest.helpers.Assertions.notNull;
import static io.restassured.RestAssured.given;

/**
 * Class containing methods that wrap requests to Booking API
 */
public class BookingRequests {

    /**
     * Request to create a booking using any object (Map or Booking) for json body
     *
     * @param booking Booking (or other type) object as create request
     * @return RestAssured response
     */
    public Response createBooking(Object booking) {
        return given().header("Content-Type", "application/json")
                .when().body(booking)
                .post(EndpointUri.BOOKINGS.getUri());
    }

    /**
     * Request to create a booking using any object (Map or Booking) for json body
     *
     * @param booking Booking (or other type) object as create request
     * @return successful response body Booking
     */
    public Booking createBookingSuccess(Object booking) {
        return given().header("Content-Type", "application/json")
                .when().body(booking)
                .post(EndpointUri.BOOKINGS.getUri())
                .body().as(Booking.class);
    }

    /**
     * Request to list booking ids filtered by one or more query parameters
     *
     * @param firstName first name
     * @param lastName  last name
     * @param checkIn   check in
     * @param checkOut  check out
     * @return RestAssured response
     */
    public Response listBookings(String firstName, String lastName, String checkIn, String checkOut) {
        // Add query parameters to Map, if defined and not null/empty
        Map<String, Object> queryParams = new HashMap<>();
        if (notNull(firstName))
            queryParams.put("firstname", firstName);
        if (notNull(lastName))
            queryParams.put("lastname", lastName);
        if (notNull(checkIn))
            queryParams.put("checkin", checkIn);
        if (notNull(checkOut))
            queryParams.put("checkout", checkOut);

        return given().queryParams(queryParams)
                .when().get(EndpointUri.BOOKINGS.getUri());
    }

    /**
     * Request to list booking ids of all available bookings
     *
     * @return RestAssured response
     */
    public Response listBookings() {
        return listBookings(null, null, null, null);
    }

    /**
     * Request to list booking ids filtered by one or more query parameters
     *
     * @param firstName first name
     * @param lastName  last name
     * @param checkIn   check in
     * @param checkOut  check out
     * @return List of booking ids
     */
    public List listBookingsSuccess(
            String firstName,
            String lastName,
            String checkIn,
            String checkOut
    ) {
        return this.listBookings(firstName, lastName, checkIn, checkOut)
                .body().as(List.class);
    }

    /**
     * Request to update existing booking with partial data
     *
     * @param token     access token needed for this request
     * @param bookingId id of the booking that needs to be updated
     * @param booking   body of the update request
     * @return RestAssured response
     */
    public Response partialUpdateBooking(String token, int bookingId, Object booking) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .when().body(booking)
                .pathParam("id", bookingId)
                .patch(EndpointUri.BOOKING_DETAILS.getUri());
    }

    /**
     * Request to update booking using wrong id format
     *
     * @param token     access token needed for this request
     * @param bookingId id of the booking that needs to be updated in invalid format
     * @param booking   body of the update request
     * @return RestAssured response
     */
    public Response partialUpdateBookingBadId(String token, String bookingId, Object booking) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .when().body(booking)
                .pathParam("id", bookingId)
                .patch(EndpointUri.BOOKING_DETAILS.getUri());
    }

    /**
     * Request to update existing booking with partial data
     *
     * @param token     access token needed for this request
     * @param bookingId id of the booking that needs to be updated
     * @param booking   body of the update request
     * @return success response body Booking
     */
    public Booking partialUpdateBookingSuccess(String token, int bookingId, Object booking) {
        return this.partialUpdateBooking(token, bookingId, booking)
                .body().as(Booking.class);
    }

    /**
     * Request to retrieve a single booking by id
     *
     * @param bookingId id of the booking requested to be listed
     * @return RestAssured response
     */
    public Response getBooking(int bookingId) {
        return given()
                .header("Accept", "application/json")
                .when().pathParam("id", bookingId)
                .get(EndpointUri.BOOKING_DETAILS.getUri());
    }

    /**
     * Request to retrieve a single booking by id
     *
     * @param bookingId id of the booking requested to be listed
     * @return success response body Booking
     */
    public Booking getBookingSuccess(int bookingId) {
        return this.getBooking(bookingId)
                .body().as(Booking.class);
    }

    /**
     * Request to delete a booking by id
     *
     * @param token     access token needed for this request
     * @param bookingId id of the booking requested to be deleted
     * @return RestAssured response
     */
    public Response deleteBooking(String token, int bookingId) {
        return given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .when().pathParam("id", bookingId)
                .delete(EndpointUri.BOOKING_DETAILS.getUri());
    }

    /**
     * Request to delete a booking by invalid id
     *
     * @param token     access token needed for this request
     * @param bookingId id of the booking in invalid format
     * @return RestAssured response
     */
    public Response deleteBookingBadId(String token, String bookingId) {
        return given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .when().pathParam("id", bookingId)
                .delete(EndpointUri.BOOKING_DETAILS.getUri());
    }

}
