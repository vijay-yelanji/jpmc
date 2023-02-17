package com.jpmc.theater;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    private static final Logger logger = LoggerFactory.getLogger(Reservation.class);

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }
    
    
    public Reservation(Customer customer, int  sequenceOfTheDay, int audienceCount) {
        this.customer = customer;
        this.audienceCount = audienceCount;
        this.showing = MovieUtil.showMap.get(sequenceOfTheDay);
    }
    

    public double totalFee() {
        return showing.getMovieFee() * audienceCount;
    }
    
   
    public double totalFeeAfterDiscount() {
        return showing.getMovie().calculateTicketPrice(showing) * audienceCount;
    }
    
    
    public String getReceipt() {
    	Gson gson =  new MovieUtil().getGson();
    	
    	JsonObject obj = new JsonObject();
    	obj.addProperty("Customer", customer.getCustomer());
    	obj.addProperty("Title", showing.getMovie().getTitle());
    	obj.addProperty("ShowTime",  showing.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    	obj.addProperty("Total Tickets", audienceCount);
    	obj.addProperty("Total Fee", totalFeeAfterDiscount());
    	
    	String str = gson.toJson(obj);
    	logger.info(str);
    	
    return str;
        		
        		
        
    }
}