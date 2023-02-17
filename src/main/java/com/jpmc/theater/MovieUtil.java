package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MovieUtil {
	
	protected static List<Showing> schedule;
	protected static Map <Integer, Showing> showMap;
	protected static LocalDateProvider provider = LocalDateProvider.singleton();
	
	static {
		
			Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
			Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
			Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);

			schedule = List.of(new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
					new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
					new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
					new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
					new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
					new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
					new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
					new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
					new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0))));
			showMap = new HashMap<>();
			schedule.forEach(s -> showMap.put(s.getSequenceOfTheDay(),s) );
			
	}
	
	private Gson gson;
	
	public MovieUtil() {
		
		 this.gson = init();
	}
	
	public Gson getGson() {
		return this.gson;
	}
	
	
	private Gson init() {

		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTimeDeserializer());

		gsonBuilder.registerTypeAdapter(Duration.class, new DurationDeSerializer());

		gsonBuilder.registerTypeAdapter(Duration.class, new DurationSerializer());

		return gsonBuilder.setPrettyPrinting().create();

	}

	class TestModel {

		private LocalDateTime localDateTime;

		private Duration runningTime;

		public TestModel(LocalDateTime localDateTime, Duration runningTime) {

			this.localDateTime = localDateTime;
			this.runningTime = runningTime;

		}

		public LocalDateTime getLocalDateTime() {
			return localDateTime;
		}

		public Duration getRunningTime() {
			return runningTime;
		}
	}

	class DurationSerializer implements JsonSerializer<Duration> {

		@Override
		public JsonElement serialize(Duration durtaion, Type srcType, JsonSerializationContext context) {
			return new JsonPrimitive(durtaion.toMinutes());
		}
	}

	class DurationDeSerializer implements JsonDeserializer<Duration> {

		@Override
		public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Duration.ofMinutes(json.getAsLong());
		}
	}

	class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

		@Override
		public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
			return new JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime));
		}
	}

	class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

		@Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return LocalDateTime.parse(json.getAsString(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.ENGLISH));
		}
	}

	public static void main(String[] args) {
		MovieUtil util = new MovieUtil();
		
		

		Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
		
		List<Showing> schedule = List.of(
	            new Showing(turningRed, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))),  
	            new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)))
	            );
	            
		// Convert to JSON

		System.out.println(util.getGson().toJson(schedule));


	}



}
