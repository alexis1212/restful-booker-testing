package com.aleksandrab.restfulBookerTest.glue.bookingApi;

import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import com.aleksandrab.restfulBookerTest.requestcontext.BookingRequests;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.util.Map;

import static com.aleksandrab.restfulBookerTest.helpers.Assertions.notNull;

/**
 * Glue methods for steps to retrieve a booking by id
 */
public class GetBookingSteps {
    private TestContext testContext;

    private BookingRequests bookingRequests = new BookingRequests();
    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public GetBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Assertion that the data of booking was stored properly, we can retrieve it and it matches data
     *
     * @param dataTable table containing expected updated object data
     */
    @Then("the booking should contain following data:")
    public void theBookingShouldContainFollowingData(DataTable dataTable) {
        // Reading data from input DataTable and converting it to Map
        Map<String, String> updatedBookingMap = dataTable.transpose().asMap(String.class, String.class);

        // Retrieving stored Booking object by id and converting it to Map
        int bookingId = testContext.getBooking().getBookingid();
        Booking booking = bookingRequests.getBookingSuccess(bookingId);
        Map<String, Object> bookingObjectMap = objectMapper.convertValue(booking, Map.class);
        Map<String, Object> bookingDatesMap = objectMapper.convertValue(booking.getBookingdates(), Map.class);

        // For each element in dataTable, assert that there is an attribute with matching value in retrieved object
        updatedBookingMap.forEach((key, value) -> {
            if (notNull(value)) {
                if (key.equals("checkin") || key.equals("checkout"))
                    Assert.assertEquals(bookingDatesMap.get(key).toString(), value);
                else
                    Assert.assertEquals(bookingObjectMap.get(key).toString(), value);

            }
        });
    }

    /**
     * Assertion that the object we are trying to retrieve doesn't exist
     */
    @Then("retrieving it should result in not found response")
    public void retrievingItShouldResultInNotFoundResponse() {
        bookingRequests.getBooking(testContext.getBooking().getBookingid())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
