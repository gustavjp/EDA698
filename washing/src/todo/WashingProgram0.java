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
 *      <LI>Switches off heating
 *      <LI>Switches off spin
 *      <LI>Switches off pump
 * </UL>
 */
class WashingProgram0 extends WashingProgram {

    // ------------------------------------------------------------- CONSTRUCTOR

    /**
     * @param   mach             The washing machine to control
     * @param   speed            Simulation speed
     * @param   tempController   The TemperatureController to use
     * @param   waterController  The WaterController to use
     * @param   spinController   The SpinController to use
     */
    public WashingProgram0(AbstractWashingMachine mach,
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
        // Switch of temp regulation
        myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
        // Switch off spin
        mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
        // Set water regulation to idle => drain pump stops
        myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
    }
}
