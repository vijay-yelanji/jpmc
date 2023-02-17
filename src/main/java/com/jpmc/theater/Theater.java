package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;;

public class Theater {


	private static final Logger logger = LoggerFactory.getLogger(Theater.class);
	

	public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
		Showing showing;
		try {
			showing = MovieUtil.schedule.get(sequence - 1);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
		}
		return new Reservation(customer, showing, howManyTickets);
	}

	public int isScheduleExists() {
		return MovieUtil.schedule.size();
	}

	public List<Showing> getSchedule() {
		return MovieUtil.schedule;
	}

	public void printSchedule() {

		logger.info("Current time  : {}", MovieUtil.provider.currentDate());
		logger.info("===================================================");
		MovieUtil.schedule.forEach(
				s -> logger.info(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " "
						+ humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee()));
		logger.info("===================================================");
	}

	public void printJSONSchedule() {

		logger.info("Current time  : {}", MovieUtil.provider.currentDate());
		logger.info("===================================================");
		Gson gson = new MovieUtil().getGson();
		logger.info(gson.toJson(MovieUtil.schedule));
		logger.info("===================================================");
	}

	public String humanReadableFormat(Duration duration) {
		long hour = duration.toHours();
		long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

		return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin,
				handlePlural(remainingMin));
	}

	// (s) postfix should be added to handle plural correctly
	private String handlePlural(long value) {
		if (value == 1) {
			return "";
		} else {
			return "s";
		}
	}

	public static void main(String[] args) {
		Theater theater = new Theater();
		theater.printSchedule();
		var customer = new Customer("John Doe", "unused-id");
		logger.info(" Total fee for the show {} at Sequence {} and at time {} is {}",
				theater.getSchedule().get(0).getMovie().getTitle(), theater.getSchedule().get(0).getSequenceOfTheDay(),
				theater.getSchedule().get(0).getStartTime(),
				new Reservation(customer, theater.getSchedule().get(0), 3).totalFeeAfterDiscount());
		logger.info("SequenceOfTheDay : " + theater.getSchedule().get(0).getSequenceOfTheDay());
		logger.info("IsSpecial? : " + theater.getSchedule().get(0).getMovie().getSpecialCode());
		logger.info("TicketPrice : " + theater.getSchedule().get(0).getMovie().getTicketPrice());
		logger.info("StartTime : " + theater.getSchedule().get(0).getStartTime());
		logger.info("TicketPriceAfterDiscount : "
				+ new Reservation(customer, theater.getSchedule().get(0), 3).totalFeeAfterDiscount());

		theater.printJSONSchedule();

	}
}
