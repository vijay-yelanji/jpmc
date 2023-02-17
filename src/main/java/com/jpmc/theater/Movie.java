package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Movie {
	
	 private static final Logger  logger = LoggerFactory.getLogger(Movie.class);

	 
    private static int MOVIE_CODE_SPECIAL = 1;
    private static int MIN_HOUR = 11; // 11 AM
    private static int MAX_HOUR = 16;  // 4 PM


    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
    
    public double getSpecialCode() {
        return specialCode;
    }
    

    public double calculateTicketPrice(Showing showing) {
    	logger.info("Calculating TicketPrice");
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing.getStartTime());
    }

    private double getDiscount(int showSequence, LocalDateTime startTime) {
    	logger.info("Calculating Discount using showSequence {} amd startTime {}",showSequence, startTime);
    	int hour = startTime.getHour();
    	int min = startTime.getMinute();
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
        	logger.info("20% discount for special movie");
            specialDiscount = ticketPrice * 0.2;  
        }

        double sequenceDiscount = 0;
        if (showSequence == 1) {
        	logger.info("$3 discount for 1st show");
            sequenceDiscount = 3; 
        } else if (showSequence == 2) {
        	logger.info("$2 discount for 2nd show");
            sequenceDiscount = 2; 
        }else if ( ( hour >=  MIN_HOUR &&  hour < MAX_HOUR ) || ( hour == MAX_HOUR && min == 0 ) ) {
        	logger.info("25% discount between 11 AM and 16 PM");
            sequenceDiscount =  ticketPrice * 0.25;   
        }else if (showSequence == 7) {
        	logger.info("$1 discount for 7th show");
            sequenceDiscount = 1; 
        }
        // biggest discount wins
        logger.info("SpecialDiscount : {} SequenceDiscount : {} ",specialDiscount, sequenceDiscount);
        return specialDiscount > sequenceDiscount ? specialDiscount : sequenceDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}