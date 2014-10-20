package todo;

import done.*;

public class WashingController implements ButtonListener {	
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tempController;
	private WaterController waterController;
	private SpinController spinController;
	private WashingProgram wp;
    private boolean programStarted;
    private boolean wasInterrupted;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
    	this.theMachine = theMachine;
    	this.theSpeed = theSpeed;
    	tempController = new TemperatureController(theMachine, theSpeed);
    	waterController = new WaterController(theMachine, theSpeed);
    	spinController = new SpinController(theMachine, theSpeed);
    	waterController.start();
    	spinController.start();
    	tempController.start();
        programStarted = false;
        wasInterrupted = false;
    }

    public void processButton(int theButton) {
    	switch(theButton){
    	case 0:
            System.out.println("0 "+ programStarted);
            if(programStarted){
                programStarted = false;
                wasInterrupted = true;
                wp.interrupt();
                wp = null;
            }

    		break;

    	case 1:

            if(programStarted == false && !wasInterrupted){
                wp = new WashingProgram1(theMachine, theSpeed, tempController, waterController, spinController);
                programStarted = true;
                System.out.println(Thread.activeCount());
                wp.start();
            }
    		break;

    	case 2:

            if(programStarted == false && !wasInterrupted){
                wp = new WashingProgram2(theMachine, theSpeed, tempController, waterController, spinController);
                programStarted = true;
                System.out.println(Thread.activeCount());
                wp.start();
            }
    		break;

    	case 3:

            if(programStarted == false){
                wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
                if(wasInterrupted){
                    programStarted = true;
                    wp.start();
                    programStarted = false;
                    wasInterrupted = false;
                }

            }
    		break;
            default:
                System.out.println("faojdfnauifn");
        }
    }
}
