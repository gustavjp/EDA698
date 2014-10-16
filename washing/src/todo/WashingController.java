package todo;

import done.*;

public class WashingController implements ButtonListener {	
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tempController;
	private WaterController waterController;
	private SpinController spinController;
	private WashingProgram wp;
	// TODO: add suitable attributes
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		// TODO: implement this constructor
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
		// TODO: implement this method
    	switch(theButton){
    	case 0: //stop
            wp = new WashingProgram0(theMachine, theSpeed, tempController, waterController, spinController);
            wp.start();
    		break;
    	case 1: //program1 - colour wash
    		wp = new WashingProgram1(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;
    	case 2: //program2 - white wash
    		wp = new WashingProgram2(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;
    	case 3: //program3 - drain
    		wp = new WashingProgram3(theMachine, theSpeed, tempController, waterController, spinController);
    		wp.start();
    		break;
    	}
    }
}
