package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class SpinController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private SpinEvent event;
	private int mode;
	private WashingProgram wp;
	private long startTime;
	private int SPIN_LEFT = 1;
	private int SPIN_RIGHT = 2;
	// TODO: add suitable attributes

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.mach = mach;
		mode = SpinEvent.SPIN_OFF;
	}

	public void perform() {
		event = (SpinEvent) mailbox.tryFetch();
		if (event != null) {
			mode = event.getMode();
			wp = (WashingProgram) event.getSource();
		}
		
		mach.setSpin(mode);
		if((mode != SPIN_LEFT) || (mode != SPIN_RIGHT)){
			
		}
		else {
			
		}
		 
		// TODO: implement this method
	}
}
