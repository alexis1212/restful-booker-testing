package com.aleksandrab.restfulBookerTest.glue.bookingApi;

import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.BookingDates;
import com.aleksandrab.restfulBookerTest.requestcontext.BookingRequests;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking.initialBookingRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Glue methods for steps to partially update booking
 */
public class PartialUpdateBookingSteps {

    private TestContext testContext;

    private BookingRequests bookingRequests = new BookingRequests();

    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public PartialUpdateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Request to update a booking from test context with partial data
     *
     * @param dataTable table containing data to be updated on a booking
     */
    @When("I attempt to update booking with partial data:")
    public void iAttemptToUpdateBookingWithPartialData(DataTable dataTable) {
        // Reading data from input DataTable and converting it to Map
        Map<String, String> bookingMap = dataTable.transpose().asMap(String.class, String.class);

        // Converting generated table to booking object using Jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Booking bookingObject = mapper.convertValue(bookingMap, Booking.class);

        // Setting dates from DataTable
        bookingObject.setBookingdates(
                new BookingDates(
                        bookingMap.get("checkin"),
                        bookingMap.get("checkout")
                )
        );

        // Sending request (fetching needed token and id from TestContext) and storing response to StoredResponse DTO
        testContext.setStoredResponse(
                bookingRequests.partialUpdateBooking(
                        testContext.getToken(),
                        testContext.getBooking().getBookingid(),
                        bookingObject
                )
        );
    }

    /**
     * Request to update a booking with an id that doesn't exist
     */
    @When("I attempt to update a booking that doesn't exist")
    public void iAttemptToUpdateABookingThatDoesntExist() {
        testContext.setStoredResponse(
                bookingRequests.partialUpdateBooking(
                        testContext.getToken(),
                        99999999,
                        initialBookingRequest()
                )
        );
    }

    /**
     * Request to update booking with unsupported info in request body
     */
    @When("I attempt to update a booking with wrong information")
    public void iAttemptToUpdateABookingWithWrongInformation() {
        Map<String, String> bookingObject = new HashMap<>();
        bookingObject.put("invalidParameter", "someStrangeValue");
        testContext.setStoredResponse(
                bookingRequests.partialUpdateBooking(
                        testContext.getToken(),
                        testContext.getBooking().getBookingid(),
                        bookingObject
                )
        );
    }

    /**
     * Request to update booking by invalid id
     */
    @When("I attempt to update a booking with an id in incorrect format")
    public void iAttemptToUpdateABookingWithAnIdInIncorrectFormat() {
        testContext.setStoredResponse(
                bookingRequests.partialUpdateBookingBadId(
                        testContext.getToken(),
                        "thisIsUnsupportedFormat",
                        initialBookingRequest()
                )
        );
    }

    /**
     * Request to update booking with an empty body
     */
    @When("I attempt to update booking with empty object")
    public void iAttemptToUpdateBookingWithEmptyObject() {
        Map<String, String> bookingObject = new HashMap<>();
        testContext.setStoredResponse(
                bookingRequests.partialUpdateBooking(
                        testContext.getToken(),
                        testContext.getBooking().getBookingid(),
                        bookingObject
                )
        );
    }

    /**
     * Assertion that received response matches expected schema and status code
     */
    @Then("the booking should be successfully updated")
    public void theBookingShouldBeSuccessfullyUpdated() {
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/booking-details-schema.json"));
    }

    /**
     * Assertion that we received an error status code
     */
    @Then("I should receive an error that the method is not allowed")
    public void iShouldReceiveAnErrorThatTheMethodIsNotAllowed() {
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    /**
     * Assertion that we received a forbidden error
     */
    @Then("I should receive an error saying that I am not authenticated")
    public void iShouldReceiveAnErrorSayingThatIAmNotAuthenticated() {
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
