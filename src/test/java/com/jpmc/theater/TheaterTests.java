package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater();
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
        assertEquals(50, reservation.totalFee());
    }

    void printMovieSchedule() throws Exception {
        Theater theater = new Theater();
        if(theater.isScheduleExists() > 0) {
        	assertTrue(theater.isScheduleExists() > 0);
        	theater.printSchedule();

        }
        else
        	assertEquals(0, theater.isScheduleExists());

    }
}
