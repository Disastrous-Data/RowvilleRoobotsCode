package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

    private boolean stop = false;
    private double axis0Offset = -0.02;
    private double axis1Offset = 0;

    //CLAW
    private boolean clawState = false;  // True is in
    boolean handIsOnCooldown = false;  // Cooldown

    //LEDs 
    private boolean LEDState = false;
    private boolean ledisoncooldown = false;
    private boolean RainbowCooldown = false;
    private boolean LEDStateRainbow = false;
    
    // Invoked periodically during teleop
    public void Invoke(TankDrive drive) {
        HardwareStates states = new HardwareStates();    

        // boolean sb = drive.Controller.getRawButton(1);  // TODO: DOES THIS WORK?????
        // boolean usb = drive.Controller.getRawButton(2);
        // if (sb) stop = true;
        // if (usb) stop = false;

        SmartDashboard.putBoolean("stopped", stop);
        if (stop) {
            drive.Update(states);  // Updating a state with no changes will stop everything
            return;
        }

        // |---------------------------------------------------|
        // | ALL CODE GOES AFTER THIS SO THAT STOP LOGIC WORKS |
        // |---------------------------------------------------|

        //
        if (drive.Controller.getRawButton(4)) {
            states.ArmInOutMotors = -0.6;
        }
        else if (drive.Controller.getRawButton(6)) {
            states.ArmInOutMotors = 0.6;
        }
        
        // Winch code for arm
        if (drive.Controller.getRawButton(3) ) {
            states.ArmUpDownMotors = 0.5;
        } 
        
        if (drive.Controller.getRawButton(5) && drive.Hardware.ArmLimitSwitch.get()){
            states.ArmUpDownMotors = -0.5;
        }

        //SmartDashboard.putBoolean("ArmWinchLimitSwitch", drive.Hardware.ArmLimitSwitch.get());

        //Code for ClawPiston 
        if (!drive.Controller.getRawButton(1)) {
            handIsOnCooldown = false;
        }
        if (drive.Controller.getRawButton(1) && !handIsOnCooldown) {  // Trigger
            clawState = !clawState;
            drive.SetClawPiston(clawState);
            handIsOnCooldown = true;
        }

        if (drive.Controller.getRawButton(2)) {
            clawState = !clawState;
            drive.SetClawPiston(clawState);
        }
        
        // if(drive.Controller.getRawButton(2)) {  // Side Button
        //     drive.SetClawPiston(true);
        // }

        //Code for LED's 
        if (!drive.Controller.getRawButton(12)) {
            ledisoncooldown = false;
        }
        if (drive.Controller.getRawButton(12) && !ledisoncooldown) {  // Trigger
            LEDState = !LEDState;
            drive.Leds.setledmode(LEDState);
            ledisoncooldown = true;
        }

        //Rainbow LEDs
        if (!drive.Controller.getRawButton(11)) {
            RainbowCooldown = false;
        }
        if (drive.Controller.getRawButton(11) && !RainbowCooldown) {  // Trigger
            LEDStateRainbow = !LEDStateRainbow;
            RainbowCooldown = true;
        }
        if (LEDStateRainbow) {
            drive.Leds.LEDRainbow();
        }
        
        double fb = drive.Controller.getY() + axis1Offset / 2;
        double lr = drive.Controller.getX() + axis0Offset / 2;
        states.LeftDriveMotors = fb + -lr;
        states.RightDriveMotors = fb + lr;
        drive.Update(states);
    }

}