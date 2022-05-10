package org.suai.lab12.chat.time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TimeManager {
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	private static final ZoneId zoneId = ZoneId.systemDefault();
	private static Clock clock = Clock.systemDefaultZone();
	private static TimeUnit timeUnit = TimeUnit.SECONDS;

	static LocalDateTime getCurrentTime() {
		return LocalDateTime.now(clock);
	}

	private static LocalDate getCurrentDay() {
		return LocalDate.now(clock);
	}

	public static LocalDateTime getLocalDateTimeFrom(String time) {
		LocalDate today = TimeManager.getCurrentDay();
		DateTimeFormatter format = DateTimeFormatter.ofPattern(TimeManager.DATE_TIME_FORMAT);
		return LocalDateTime.parse(today + " " + time, format);
	}

	static long until(LocalDateTime dateTime) {
		return getCurrentTime().until(dateTime, ChronoUnit.SECONDS);
	}

	static TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public static void useFixedClockAt(LocalDateTime time) {
		clock = Clock.fixed(time.atZone(zoneId)
									.toInstant(), zoneId);
	}

	public static void useSystemDefaultZoneClock() {
		clock = Clock.systemDefaultZone();
	}

	// for tests
	public static void setTimeUnit(TimeUnit timeUnit) {
		TimeManager.timeUnit = timeUnit;
	}

	// for tests
	public static void useFixedClockAt(String time) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(TimeManager.DATE_TIME_FORMAT);
		LocalDateTime parsed = LocalDateTime.parse(time, format);
		useFixedClockAt(parsed);
	}
}