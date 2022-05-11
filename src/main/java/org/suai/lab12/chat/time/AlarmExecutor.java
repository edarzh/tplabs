package org.suai.lab12.chat.time;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
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
		alarms.put(name, new PriorityBlockingQueue<>());
	}

	public void addAlarm(String name, LocalDateTime dateTime, Callable<Boolean> task, Runnable taskIfMissed) {
		Queue<Alarm> alarmQueue = alarms.get(name);
		if (alarmQueue != null) {
			Alarm alarm = new Alarm(dateTime, task, taskIfMissed, alarmQueue);

			alarmQueue.add(alarm);
			scheduler.schedule(alarm, TimeManager.until(dateTime), TimeManager.getTimeUnit());
		}
	}

	public void runMissedIfAny(String name) {
		Queue<Alarm> alarmQueue = alarms.get(name);
		if (alarmQueue != null) {
			Alarm alarm;
			while ((alarm = alarmQueue.peek()) != null) {
				if (TimeManager.until(alarm.getDateTime()) < 0) {
					alarm.runMissed();
				} else {
					break;
				}
			}
		}
	}

	private record Alarm(LocalDateTime dateTime, Callable<Boolean> task, Runnable taskIfMissed, Queue<Alarm> alarmQueue)
			implements Runnable, Comparable<Alarm> {

		LocalDateTime getDateTime() {
			return dateTime;
		}

		@Override
		public void run() {
			try {
				if (task.call()) {
					alarmQueue.remove(this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		void runMissed() {
			taskIfMissed.run();
			alarmQueue.remove(this);
		}

		@Override
		public int compareTo(Alarm o) {
			return dateTime.compareTo(o.dateTime);
		}
	}
}