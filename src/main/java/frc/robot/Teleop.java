package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

    private boolean stop = false;
    private double axis0Offset = -0.02;
    private double axis1Offset = 0;

    //CLAW
    private boolean handIsOnCooldown = false;  // Cooldown

    private int intakeDirection = 0;  // What direction is the intake spinning?
    private double intakeChangeCooldown = 0;
    private static final double intakeChangeMaxCooldown = 0.3;
    private boolean intakeStallDetectionEnabled = true;
    private double intakeStallDetectionCooldown = 0;
    private boolean intakeStallDetectionOnCooldown = false;

    private double timeSinceLastTickTimestamp = 0;

    //LEDs 
    //private boolean LEDState = false;
    //private boolean ledisoncooldown = false;
    //private boolean RainbowCooldown = false;
    //private boolean LEDStateRainbow = false;

    private static final double stallThreshHold = 0.5;
    private static final double intakeStallThreshHold = 10;
    
    // Invoked periodically during teleop
    public void Invoke(TankDrive drive, double time) {
        HardwareStates states = new HardwareStates();   
        
        double cLastTick = time - timeSinceLastTickTimestamp;
        timeSinceLastTickTimestamp = time;

        if (intakeStallDetectionCooldown > 0) {
            intakeChangeCooldown -= cLastTick;
        }
        if (intakeStallDetectionCooldown < 0) {
            intakeStallDetectionCooldown = 0;
        }

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

        // Intake toggling, it has a cooldown
        boolean intakeCaptured = drive.Hardware.Intake.getOutputCurrent() > intakeStallThreshHold;
        if (intakeChangeCooldown == 0) {
            boolean didStallEnable = false;
            boolean didAnyControl = false;
            // if (intakeCaptured && intakeStallDetectionEnabled) {
            //     didStallEnable = true;
            // }
            if (drive.Controller.getRawButton(5)) {  // Direction -1
                didAnyControl = true;
                if (intakeDirection == -1) {
                    intakeDirection = 0;
                    intakeChangeCooldown = intakeChangeMaxCooldown;
                } else {
                    intakeDirection = -1;
                    intakeChangeCooldown = intakeChangeMaxCooldown;
                    // intakeStallDetectionEnabled = false;
                    // intakeStallDetectionOnCooldown = true;
                    // intakeStallDetectionCooldown = 0.5;
                }
            }
            else if (drive.Controller.getRawButton(3)) {  // Direction 1
                didAnyControl = true;
                if (intakeDirection == 1) {
                    intakeDirection = 0;
                    intakeChangeCooldown = intakeChangeMaxCooldown;
                } else {
                    intakeDirection = 1;
                    intakeChangeCooldown = intakeChangeMaxCooldown;
                    // intakeStallDetectionEnabled = false;
                    // intakeStallDetectionOnCooldown = true;
                    // intakeStallDetectionCooldown = 0.5;
                }
            }
            // if (!didAnyControl && didStallEnable) {
            //     intakeDirection = 0;
            // }
        } else {
            intakeChangeCooldown -= cLastTick;
            if (intakeChangeCooldown < 0) intakeChangeCooldown = 0;
        }

        SmartDashboard.putBoolean("isIntakeCaptured", intakeCaptured);
        SmartDashboard.putNumber("intakeDirection", intakeDirection);
        SmartDashboard.putBoolean("isIntakeStallDetectionEnabled", intakeStallDetectionEnabled);
        SmartDashboard.putNumber("intakeChangeCooldown", intakeChangeCooldown);
        SmartDashboard.putNumber("intakeCurrentMotorOutput", drive.Hardware.Intake.getOutputCurrent());

        states.Intake = intakeDirection;

        // if (!intakeStallDetectionEnabled && intakeStallDetectionOnCooldown && intakeStallDetectionCooldown == 0) {
        //     intakeStallDetectionEnabled = true;
        //     intakeStallDetectionOnCooldown = false;
        // }
        
        // Arm up down
        if (drive.Controller.getRawButton(6) ) {
            states.Arm = 0.5;
        }

        // Arm Up (With limit switch and limit bypass)
        if (drive.Controller.getRawButton(4) && (drive.Hardware.ArmLimitSwitch.get() || drive.Controller.getRawButton(10))){
            states.Arm = -0.5;
        }

        //SmartDashboard.putBoolean("ArmWinchLimitSwitch", drive.Hardware.ArmLimitSwitch.get());

        //Code for arm in out 
        if (drive.Controller.getRawButton(1)) {
            states.ArmInOutMotors = 0.6;
        }

        if (drive.Controller.getRawButton(2)) {
            states.ArmInOutMotors = -0.6;
        }
        
        // if(drive.Controller.getRawButton(2)) {  // Side Button
        //     drive.SetClawPiston(true);
        // }

        // for (int i = 0; i < 20; i++) {
        //     SmartDashboard.putBoolean("button" + i + "IsPressed", drive.Controller.getRawButton(i));
        // }
        
        double fb = drive.Controller.getY() + axis1Offset / 2;
        double lr = drive.Controller.getX() + axis0Offset / 2;
        lr = lr * 0.5;
        double leftDriveValue = fb + -lr;
        double rightDriveValue = fb + lr;

        states.LeftDriveMotors = leftDriveValue;
        states.RightDriveMotors = rightDriveValue;

        drive.Update(states);
        SmartDashboard.putNumber("outputCurrent", drive.Hardware.LeftMotor2.getOutputCurrent());
        SmartDashboard.putBoolean("isStalled", drive.Hardware.LeftMotor2.getOutputCurrent() > stallThreshHold);
        SmartDashboard.putBoolean("isLimitSwitch", drive.Hardware.ArmLimitSwitch.get());
    }

}