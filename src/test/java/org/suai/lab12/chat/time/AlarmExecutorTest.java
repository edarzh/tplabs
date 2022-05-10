package org.suai.lab12.chat.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

class AlarmExecutorTest {

	@Test
	public void test() throws IOException, InterruptedException {
		var originalOut = System.out;
		var baos = new ByteArrayOutputStream();
		var newOut = new PrintStream(baos);
		System.setOut(newOut);

		testAlarm();

		System.out.flush();
		System.setOut(originalOut);

		var expected = new String(Files.readAllBytes(Paths.get("src/test/resources/lab12/alarmExecutor1-after.txt")));

		Assertions.assertEquals(expected, baos.toString());
	}

	private void testAlarm() throws InterruptedException {
		TimeManager.useFixedClockAt("2016-10-06 12:00");
		TimeManager.setTimeUnit(TimeUnit.MICROSECONDS);

		var alarmExecutor = new AlarmExecutor();

		alarmExecutor.addUser("Ann");
		alarmExecutor.addUser("Henry");

		schedule(alarmExecutor, "13:00", "Ann");
		schedule(alarmExecutor, "16:00", "Ann");
		schedule(alarmExecutor, "13:00", "Henry");
		Thread.sleep(4);

		TimeManager.useFixedClockAt("2016-10-06 17:00");
		alarmExecutor.runMissedIfAny("Ann");
	}

	private void schedule(AlarmExecutor alarmExecutor, String time, String name) {
		var dateTime = TimeManager.getLocalDateTimeFrom(time);
		alarmExecutor.addAlarm(name,
							   dateTime,
							   () -> {
								   System.out.println(AlarmExecutor.ALARM_MESSAGE);
								   return true;
							   },
							   () -> System.out.println(AlarmExecutor.MISSED_ALARM_MESSAGE + dateTime));
	}
}