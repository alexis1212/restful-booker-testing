package com.aleksandrab.restfulBookerTest.dtos.bookingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public
class BookingDates {
    private String checkin;
    private String checkout;

    /**
     * Booking Dates generator with some default values
     */
    public static BookingDates initialBookingDates = BookingDates.builder()
                    .checkin("2023-01-01")
                    .checkout("2023-02-01")
                    .build();
}
