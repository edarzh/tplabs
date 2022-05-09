package org.suai.lab12.chat.time;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AlarmExecutor {
	public static final String ALARM_MESSAGE = "Wake up!";
	public static final String MISSED_ALARM_MESSAGE = "Missed alarm at ";

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final Map<String, Queue<Alarm>> alarms = new HashMap<>();

	public boolean containsUser(String name) {
		return alarms.containsKey(name);
	}

	public void addUser(String name) {
		alarms.put(name, new PriorityQueue<>());
	}

	public void addAlarm(String name, LocalDateTime dateTime, Runnable task, Runnable taskIfMissed) {
		Queue<Alarm> userAlarmQueue = alarms.get(name);
		if (userAlarmQueue != null) {
			Alarm alarm = new Alarm(name, dateTime, task, taskIfMissed);

			userAlarmQueue.add(alarm);
			scheduler.schedule(alarm, TimeManager.until(dateTime), TimeManager.getTimeUnit());
		}
	}

	public void runMissedIfAny(String name) {
		// TODO: implement
	}

	static class Alarm implements Runnable {
		private final String name;
		private final LocalDateTime dateTime;
		private final Runnable task;
		private final Runnable taskIfMissed;

		Alarm(String name, LocalDateTime dateTime, Runnable task, Runnable taskIfMissed) {
			this.name = name;
			this.dateTime = dateTime;
			this.task = task;
			this.taskIfMissed = taskIfMissed;
		}

		LocalDateTime getDateTime() {
			return dateTime;
		}

		@Override
		public void run() {
			task.run();
		}

		void runMissed() {
			// TODO: implement
		}
	}
}