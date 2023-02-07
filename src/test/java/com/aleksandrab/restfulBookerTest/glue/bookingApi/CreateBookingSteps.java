package com.aleksandrab.restfulBookerTest.glue.bookingApi;

import com.aleksandrab.restfulBookerTest.PropertiesReader;
import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking;
import com.aleksandrab.restfulBookerTest.dtos.bookingApi.BookingDates;
import com.aleksandrab.restfulBookerTest.requestcontext.BookingRequests;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

import static com.aleksandrab.restfulBookerTest.dtos.bookingApi.Booking.initialBookingRequest;
import static com.aleksandrab.restfulBookerTest.helpers.Assertions.notNull;

/**
 * Glue methods for steps to create bookings
 */
public class CreateBookingSteps {

    private TestContext testContext;

    private BookingRequests bookingRequests = new BookingRequests();
    private static Properties properties = new Properties();

    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public CreateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Setting Test configuration in before scenario
     */
    @BeforeAll
    public static void before_all() {
        PropertiesReader.readDataFromProperties(properties);
        RestAssured.baseURI = properties.getProperty("baseuri");
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    /**
     * Precondition - creating new booking to make sure that a booking with certain dates exists
     *
     * @param checkIn  check in date from scenario
     * @param checkOut check out date from scenario
     */
    @Given("there is a booking created with date {string} {string}")
    public void thereIsABookingCreatedWithDate(String checkIn, String checkOut) {
        // Setting initial booking request with default values
        Booking booking = initialBookingRequest();

        // Setting booking dates from inputs, in case they exist, otherwise use defaults
        String checkin = notNull(checkIn) ? checkIn : booking.getBookingdates().getCheckin();
        String checkout = notNull(checkOut) ? checkOut : booking.getBookingdates().getCheckout();
        booking.setBookingdates(new BookingDates(checkin, checkout));

        // Sending request and storing generated id to Booking DTO
        int bookingId = bookingRequests.createBooking(booking).jsonPath().get("bookingid");
        booking.setBookingid(bookingId);
        testContext.setBooking(booking);
    }

    /**
     * Precondition - creating new booking to make sure that a booking with certain name exists
     *
     * @param firstName first name from scenario
     * @param lastName  last name from scenario
     */
    @Given("there is a booking created with name {string} {string}")
    public void thereIsABookingCreatedWithName(String firstName, String lastName) {
        // Setting initial booking request with default values
        Booking booking = initialBookingRequest();

        // Setting name from inputs, in case it exists, otherwise use defaults
        String firstname = notNull(firstName) ? firstName : booking.getFirstname();
        String lastname = notNull(lastName) ? lastName : booking.getLastname();
        booking.setFirstname(firstname);
        booking.setLastname(lastname);

        // Sending request and storing generated id to Booking DTO
        int bookingId = bookingRequests.createBooking(booking).jsonPath().get("bookingid");
        booking.setBookingid(bookingId);
        testContext.setBooking(booking);
    }

    /**
     * Precondition making sure that a booking with specific features exists
     *
     * @param dataTable table with inputs for booking request from scenario
     */
    @Given("there is a booking created with data:")
    public void thereIsABookingCreatedWithData(DataTable dataTable) {
        // Reading data from input DataTable and converting it to Map
        Map<String, String> bookingMap = dataTable.transpose().asMap(String.class, String.class);

        // Converting generated table to booking object using Jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Booking bookingObject = mapper.convertValue(bookingMap, Booking.class);

        // Setting dates from DataTable
        bookingObject.setBookingdates(new BookingDates(bookingMap.get("checkin"), bookingMap.get("checkout")));

        // Sending request and storing response to storedResponse in TestContext DTO
        Response bookingResponse = bookingRequests.createBooking(bookingObject);
        int bookingId = bookingResponse.jsonPath().get("bookingid");
        bookingObject.setBookingid(bookingId);
        testContext.setStoredResponse(bookingResponse);
        testContext.setBooking(bookingObject);
    }

    /**
     * Precondition - creating new booking to be able to fetch/edit it by id later
     */
    @Given("there is a booking created")
    public void thereIsABookingCreated() {
        // Setting initial booking request with default values
        Booking booking = initialBookingRequest();

        // Sending request and storing generated id to Booking DTO
        Response bookingResponse = bookingRequests.createBooking(booking);
        int bookingId = bookingResponse.jsonPath().get("bookingid");
        booking.setBookingid(bookingId);
        testContext.setStoredResponse(bookingResponse);
        testContext.setBooking(booking);
    }
}
