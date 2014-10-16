package todo;

import done.*;

public class WashingController implements ButtonListener {	
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tempController;
	private WaterController waterController;
	private SpinController spinController;
	private WashingProgram wp;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
    	this.theMachine = theMachine;
    	this.theSpeed = theSpeed;
    	tempController = new TemperatureController(theMachine, theSpeed);
    	waterController = new WaterController(theMachine, theSpeed);
    	spinController = new SpinController(theMachine, theSpeed);
    	waterController.start();
    	spinController.start();
    	tempController.start();
    }

    public void processButton(int theButton) {
    	switch(theButton){
    	case 0:
            wp = new WashingProgram0(theMachine, theSpeed, tempController, waterController, spinController);
            wp.start();
    		break;

    	case 1:
    		wp = new WashingProgram1(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;

    	case 2:
    		wp = new WashingProgram2(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;

    	case 3:
    		wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;
    	}
    }
}
