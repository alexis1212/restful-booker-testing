package com.aleksandrab.restfulBookerTest.glue.bookingApi;

import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import com.aleksandrab.restfulBookerTest.requestcontext.BookingRequests;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Glue methods for steps to get booking ids
 */
public class GetBookingIdsSteps {

    private TestContext testContext;

    private BookingRequests bookingRequests = new BookingRequests();

    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public GetBookingIdsSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Step to fetch all bookings filtered by name and storing result in storedResponse
     *
     * @param firstName first name
     * @param lastName  last name
     */
    @When("I attempt to filter bookings by name {string} {string}")
    public void iAttemptToFilterBookingsByNameFirstnameLastname(String firstName, String lastName) {
        testContext.setStoredResponse(
                bookingRequests.listBookings(firstName, lastName, null, null)
        );
    }

    /**
     * Step to fetch all bookings filtered by date and storing result in storedResponse
     *
     * @param checkIn  check in date
     * @param checkOut check out date
     */
    @When("I attempt to filter bookings by date {string} {string}")
    public void iAttemptToFilterBookingsByDate(String checkIn, String checkOut) {
        testContext.setStoredResponse(
                bookingRequests.listBookings(null, null, checkIn, checkOut)
        );
    }

    /**
     * Step to fetch all booking ids filtered by certain criteria
     *
     * @param firstName first name
     * @param lastName  lst name
     * @param checkIn   check in date
     * @param checkOut  check out date
     */
    @When("I attempt to filter bookings by {string} {string} {string} {string}")
    public void iAttemptToFilterBookingsBy(String firstName, String lastName, String checkIn, String checkOut) {
        testContext.setStoredResponse(
                bookingRequests.listBookings(firstName, lastName, checkIn, checkOut)
        );
    }

    /**
     * Request to fetch ids of all existing bookings and store them in storedResponse
     */
    @When("I attempt to list all available bookings")
    public void iAttemptToListAllAvailableBookings() {
        testContext.setStoredResponse(
                bookingRequests.listBookings()
        );
    }

    /**
     * Assertion that storedResponse matches expected schema and contains at least one element
     */
    @Then("I should get a list of bookings containing correct information")
    public void iShouldGetAListOfBookingsContainingCorrectInformation() {
        // Assert status code 200 and response schema
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/booking-list-schema.json"));

        // Assert retrieved list in response contains at least one element
        Assert.assertTrue(testContext.getStoredResponse().jsonPath().getList("bookingid").size() > 0);
    }

    /**
     * Assertion that we retrieved a result, but it's empty
     */
    @Then("I should get an empty list")
    public void iShouldGetAnEmptyList() {
        // Assert status code 200
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK);

        // Assert we retrieved an empty list
        Assert.assertEquals(testContext.getStoredResponse().jsonPath().getList("bookingid").size(), 0);
    }
}
