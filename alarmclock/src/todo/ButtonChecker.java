package todo;

public class ButtonChecker extends Thread {

	private Time time;

	public ButtonChecker(Time time) {

		this.time = time;

	}

	public void run() {
		while (true) {
			time.checkButtons();
			// System.out.println("startIO");
		}
	}
}
