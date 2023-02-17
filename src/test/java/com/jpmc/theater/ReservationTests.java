package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTests {

    @Test
    void totalFeeSpiderMan() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );
        // ans :  fee 12.5  seq 1 , special xx  AND  seq xxx   #ticket 3  * 12.5 
        assertEquals(37.5 , new Reservation(customer, showing, 3).totalFee());
    }
    
    
    @Test
    void totalFeeAfterDiscountSpiderMan() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );
        // ans :  fee 12.5 seq 1  , special ( 20%) 2.5  AND  seq  3$  #ticket 3  * 9.5 
        assertEquals(28.5 , new Reservation(customer, showing, 3).totalFeeAfterDiscount());
    }
    
    
    
    @Test
    void totalFeeAfterDiscountTurningRed() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
        		 new Movie("Turning Red", Duration.ofMinutes(85), 11, 0),
                7,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 30))
        );
        // ans :  fee 11 seq 7 , special 0  AND seq $1  #ticket 5  * 10 
        assertEquals(50 , new Reservation(customer, showing, 5).totalFeeAfterDiscount());
    }
    
    
    @Test
    void totalFeeAfterDiscountTheBatMan() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
        		 new Movie("The Batman", Duration.ofMinutes(95), 9, 0),
                3,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 50))
        );
        // ans :  fee 9 , seq 3   special 0 AND ( 25%) 2.25  #ticket 8  * 6.75 
        assertEquals(54 , new Reservation(customer, showing, 8).totalFeeAfterDiscount());
    }
    
    
    @Test
    void totalFeeAfterDiscountTheBatManFinal() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
        		 new Movie("The Batman", Duration.ofMinutes(95), 9, 0),
                9,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0))
        );
        // ans :  fee 9 , seq 9   special 0 AND 0  #ticket 8  * 9 
        assertEquals(72 , new Reservation(customer, showing, 8).totalFeeAfterDiscount());
    }
    
    
    @Test
    void totalFeeAfterDiscountTheBatManNextFinal() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = MovieUtil.showMap.get(8);
        // ans :  fee 12.5 , seq 9   special ( 20%) 2.5  AND 0  #ticket 2  * 10
        Reservation reserve = new Reservation(customer, showing, 2);
        assertEquals(20 , reserve.totalFeeAfterDiscount());
        reserve.getReceipt();
        
        
    }
    
    
}
