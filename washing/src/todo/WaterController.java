package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class WaterController extends PeriodicThread {
	private double level;
	private AbstractWashingMachine mach;
	private WaterEvent event;
	private int mode;
	private WashingProgram wp;
	// TODO: add suitable attributes

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (2000/speed)); // TODO: replace with suitable period
		this.mach = mach;
		mode = WaterEvent.WATER_IDLE;
	}

	public void perform() {
		// TODO: implement this method
		event = (WaterEvent) mailbox.tryFetch();
		if (event != null) {
			mode = event.getMode();
			level = event.getLevel();
			wp = (WashingProgram) event.getSource();
		}
		
		switch(mode){ 
		case WaterEvent.WATER_IDLE:
			mach.setDrain(false);
			mach.setFill(false);
			break;
			
		case WaterEvent.WATER_FILL:
			if(mach.getWaterLevel() < level){
				mach.setFill(true);
			}
			else {
				//mach.setFill(false);
				wp.putEvent(new AckEvent(this));
			}
			break;
			
		case WaterEvent.WATER_DRAIN:
			if(mach.getWaterLevel() > level){
				mach.setDrain(true);
			}
			else {
				//mach.setDrain(false);
				wp.putEvent(new AckEvent(this));
			}
			break;
			
		default:
			
			break;
		}
	}
}
