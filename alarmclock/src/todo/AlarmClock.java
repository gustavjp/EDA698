package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;
	private static MutexSem mutex;
	private Time time; //Behövs nog inte då klassen används statisk
	private Counter counter;
	private ButtonChecker io;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		mutex = new MutexSem();
		time = new Time	(i, o, sem, mutex);
		counter = new Counter(time);
		io = new ButtonChecker(time);
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {
		System.out.println("Main start");
		counter.start(); // Räknare + alarmcheckare
		io.start();

	}
}
