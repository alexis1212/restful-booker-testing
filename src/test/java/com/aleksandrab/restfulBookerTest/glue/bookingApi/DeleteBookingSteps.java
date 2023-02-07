package com.aleksandrab.restfulBookerTest.glue.bookingApi;

import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import com.aleksandrab.restfulBookerTest.requestcontext.BookingRequests;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;

import java.util.Properties;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Glue methods for steps to delete a booking
 */
public class DeleteBookingSteps {

    private TestContext testContext;

    private BookingRequests bookingRequests = new BookingRequests();

    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public DeleteBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Request to delete an existing booking by id from test context
     */
    @When("I attempt to delete an existing booking")
    public void iAttemptToDeleteAnExistingBooking() {
        testContext.setStoredResponse(
                bookingRequests.deleteBooking(
                        testContext.getToken(),
                        testContext.getBooking().getBookingid()
                )
        );
    }

    /**
     * Request to delete booking that doesn't exist
     */
    @When("I attempt to delete a booking that doesn't exist")
    public void iAttemptToDeleteABookingThatDoesnTExist() {
        testContext.setStoredResponse(
                bookingRequests.deleteBooking(
                        testContext.getToken(),
                        99999999
                )
        );
    }

    /**
     * Request to delete booking by id that is in invalid format
     */
    @When("I attempt to delete a booking with an id in incorrect format")
    public void iAttemptToDeleteABookingWithAnIdInIncorrectFormat() {
        testContext.setStoredResponse(
                bookingRequests.deleteBookingBadId(
                        testContext.getToken(),
                        "idInInvalidFormat"
                )
        );
    }

    /**
     * Assertion that we received correct response code
     */
    @Then("the booking should be successfully deleted")
    public void theBookingShouldBeSuccessfullyDeleted() {
        testContext.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_CREATED);
    }
}
