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
 * Program 2 of washing machine. Does the following:
 * <UL>Lock hatch
 *      <LI>Water in
 *      <LI>Heat to 40
 *      <LI>Spin for 15 minutes
 *      <LI>Drain water
 *      <LI>Water in
 *      <LI>Heat to 60
 *      <LI>Spin for 30 minutes
 *      <LI>Drain water
 *      <LI>Rinse 5 times, 2 minutes each
 *      <LI>Centrifuge for 5 minutes
 *      <LI>Unlocks the hatch
 * </UL>
 */
class WashingProgram2 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param   mach             The washing machine to control
	 * @param   speed            Simulation speed
	 * @param   tempController   The TemperatureController to use
	 * @param   waterController  The WaterController to use
	 * @param   spinController   The SpinController to use
	 */
	public WashingProgram2(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {

        //Lock the hatch
        myMachine.setLock(true);
        //Fill with water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
        //Wait until machine is filled with water
        mailbox.doFetch();
        //Stop filling water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
        //Set temperature to 40
        myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 38.8));
        //Wait until temperature regulator is turned
        mailbox.doFetch();
        //Spin slow
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
        //Spin for 30 minutes
        Thread.sleep(15*60*1000);
        myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
        //Turn off spin
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
        //Drain water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
        //Wait until drained
        mailbox.doFetch();
        //Fill with water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
        //Wait until machine is filled with water
        mailbox.doFetch();
        //Stop filling water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
        //Set temperature to 60
        myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 88.8));
        //Wait until temperature regulator is turned on
        mailbox.doFetch();
        //Spin slow
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
        //Spin for 30 minutes
        Thread.sleep(30*60*1000);
        myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 20.0));
        //Turn off spin
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
        //Drain water
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
        //Wait until drained
        mailbox.doFetch();
        //Rinse 5 times * 2 minutes
        for(int i = 0; i < 5; i++){
            //Fill with water
            myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
            //Wait until machine is filled with water
            mailbox.doFetch();
            //Stop filling water
            myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
            //Spin slow
            mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
            //Spin for 2 minutes
            Thread.sleep(2*60*1000);
            //Turn off spin
            mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
            //Drain water
            myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
            //Wait until drained
            mailbox.doFetch();
        }
        //Centrifuge
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
        //Spin for 5 minutes
        Thread.sleep(5*60*1000);
        //Unlock
        myMachine.setLock(false);
	}
}
