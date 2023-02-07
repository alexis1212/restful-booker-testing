package com.aleksandrab.restfulBookerTest.dtos.bookingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.aleksandrab.restfulBookerTest.dtos.bookingApi.BookingDates.initialBookingDates;

/**
 * Data transfer object to store booking information and pass it through different steps/glue files
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
    private Integer bookingid;

    /**
     * Booking Request generator with some default values
     */
    public static Booking initialBookingRequest() {
        return Booking.builder()
                .bookingdates(initialBookingDates)
                .firstname("Jason")
                .lastname("Connors")
                .totalprice(1500)
                .depositpaid(true)
                .additionalneeds("Baby bed")
                .build();
    }
}