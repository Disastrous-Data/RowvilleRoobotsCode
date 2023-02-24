package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

    private boolean stop = false;
    private double axis0Offset = -0.02;
    private double axis1Offset = 0;

    // Invoked periodically during teleop
    public void Invoke(TankDrive drive) {
        HardwareStates states = new HardwareStates();

        boolean sb = drive.Controller.getRawButton(0);
        boolean usb = drive.Controller.getRawButton(1);
        if (sb) stop = true;
        if (usb) stop = false;

        SmartDashboard.putBoolean("stopped", stop);
        if (stop) {
            drive.Update(states);  // Updating a state with no changes will stop everything
            return;
        }

        // |---------------------------------------------------|
        // | ALL CODE GOES AFTER THIS SO THAT STOP LOGIC WORKS |
        // |---------------------------------------------------|

        if (drive.Controller.getRawButton(5)) {
            states.ArmMotors = 0.4;
        }
        else if (drive.Controller.getRawButton(6)) {
            states.ArmMotors = -0.4;
        }
        
        // Winch code for pivot
        if (drive.Controller.getRawButton(3)) {
            states.WinchMotors = 0.4;
        } else if (drive.Controller.getRawButton(4)){
            states.WinchMotors = -0.4;
        }

        //Code for ClawPiston 
        if (drive.Controller.getRawButton(12)  &&  states.ClawPiston) {
            states.ClawPiston = false;
        } else if(drive.Controller.getRawButton(12)) {
            states.ClawPiston = true;
        }
        
        double fb = drive.Controller.getY() + axis1Offset / 2;
        double lr = drive.Controller.getX() + axis0Offset / 2;
        states.LeftDriveMotors = fb + -lr;
        states.RightDriveMotors = fb + lr;
        drive.Update(states);
    }

}