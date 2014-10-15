/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980812 Created
 * PP 990924 Revised
 */

package todo;

import done.*;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 * <LI>Lock hatch
 * <LI>Water in
 * <LI>Heat to 60
 * <LI>Unlocks the hatch.
 * </UL>
 */
class WashingProgram1 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param mach
	 *            The washing machine to control
	 * @param speed
	 *            Simulation speed
	 * @param tempController
	 *            The TemperatureController to use
	 * @param waterController
	 *            The WaterController to use
	 * @param spinController
	 *            The SpinController to use
	 */
	public WashingProgram1(AbstractWashingMachine mach, double speed,
			TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {
		// Lock the hatch
		myMachine.setLock(true);
		// Fill with water
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
		// Wait until machine is filled with water
		mailbox.doFetch();
		System.out.println("Done filling");
		// Stop filling water
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
		//Set temperature to 60
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 58.8));
		//Wait until temperature is reached
		mailbox.doFetch();
		System.out.println("Temperature set");
		//Spin slow
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		//Spin for 30 minutes
		//Thread.sleep(30*60*100);
		Thread.sleep(35000);
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 20.0));
		//Turn off spin
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		//Drain water
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
		//Wait until drained
		mailbox.doFetch();
		//Rinse 5 times * 2 minutes
		for(int i = 0; i < 5; i++){
			// Fill with water
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
			// Wait until machine is filled with water
			mailbox.doFetch();
			// Stop filling water
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
			//Spin slow
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			//Spin for 2 minutes
			//Thread.sleep(2*60*1000);
			Thread.sleep(2000);
			//Turn off spin
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			//Drain water
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
			//Wait until drained
			mailbox.doFetch();
		}
		//Centrifuge
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		Thread.sleep(5*60*1000);
		// Unlock
		myMachine.setLock(false);
	}
}
