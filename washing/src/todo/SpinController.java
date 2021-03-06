package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private SpinEvent event;
	private int mode, direction;
	private long lastTime;
    private double speed;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed));
		this.mach = mach;
		mode = SpinEvent.SPIN_OFF;
        direction = AbstractWashingMachine.SPIN_LEFT;
        this.speed = speed;
	}

	public void perform() {
		event = (SpinEvent) mailbox.tryFetch();
		if (event != null) {
			mode = event.getMode();
            lastTime = System.currentTimeMillis();
            switch(mode){
                case SpinEvent.SPIN_OFF:
                    mach.setSpin(SpinEvent.SPIN_OFF);
                    break;

                case SpinEvent.SPIN_SLOW:
                    mach.setSpin(SpinEvent.SPIN_SLOW);
                    break;

                case SpinEvent.SPIN_FAST:
                    mach.setSpin(SpinEvent.SPIN_FAST);
                    break;

                default:
                    break;
            }
        }

        if(mode != SpinEvent.SPIN_OFF){
            if(System.currentTimeMillis() >= lastTime + 60*1000/(int)speed){
                if(direction == AbstractWashingMachine.SPIN_LEFT){
                    direction = AbstractWashingMachine.SPIN_RIGHT;
                }
                else{
                    direction = AbstractWashingMachine.SPIN_LEFT;
                }
                lastTime = System.currentTimeMillis();
                mach.setSpin(direction);
            }
        }
	}
}
