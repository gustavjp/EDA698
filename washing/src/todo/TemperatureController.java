package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class TemperatureController extends PeriodicThread {
	private double temperature;
	private AbstractWashingMachine mach;
	private TemperatureEvent event;
	private boolean ack;
	private int mode;
	
	private WashingProgram wp;
	// TODO: add suitable attributes

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (5000/speed)); // TODO: replace with suitable period
		this.mach = mach;
		mode = TemperatureEvent.TEMP_IDLE;
		ack = false;
	}

	public void perform() {
		event = (TemperatureEvent) mailbox.tryFetch();
		if (event != null) {
			mode = event.getMode();
			temperature = event.getTemperature();
			wp = (WashingProgram) event.getSource();
			ack = false;
		}
		
		switch(mode){ 
		case TemperatureEvent.TEMP_IDLE:
			mach.setHeating(false);
			break;
			
		case TemperatureEvent.TEMP_SET:
			if(mach.getTemperature() < temperature && mach.getWaterLevel() > 0){
				mach.setHeating(true);
			}
			else {
				mach.setHeating(false);
				if(ack){
					wp.putEvent(new AckEvent(this));
					ack = true;
				}
			}
			break;
			
		default:
			
			break;
		}
		// TODO: implement this method
	}
}
