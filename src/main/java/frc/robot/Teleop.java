package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

    private boolean stop = false;
    private double axis0Offset = -0.02;
    private double axis1Offset = 0;

    public void Invoke(TankDrive drive, Hardware hardware) {
        boolean sb = hardware.LeftJoystick.getRawButton(0);
        boolean usb = hardware.LeftJoystick.getRawButton(1);
        if (sb) stop = true;
        if (usb) stop = false;

        if (hardware.LeftJoystick.getRawButton(5)) {
            hardware.Arm.set(0.4);
            SmartDashboard.putNumber("arm", 0.4);
        } 
        else if (hardware.LeftJoystick.getRawButton(6)) {
            hardware.Arm.set(-0.4);
            SmartDashboard.putNumber("arm", -0.4);
        } else{
            hardware.Arm.set(0);
            SmartDashboard.putNumber("arm", 0);
        }
        
        //Winch code for pivot
        if (hardware.LeftJoystick.getRawButton(3)) {
            hardware.WinchLeft.set(0.4);
            hardware.WinchRight.set(-0.4);
        }else if (hardware.LeftJoystick.getRawButton(4)){
            hardware.WinchLeft.set(-0.4);
            hardware.WinchRight.set(0.4);
        }
        else{
            hardware.WinchLeft.set(0);
            hardware.WinchRight.set(0);
        }



        SmartDashboard.putBoolean("stopped", stop);
        if (stop) {
            drive.SetLeft(0);
            drive.SetRight(0);
            return;
        }
        double fb = hardware.LeftJoystick.getY() + axis1Offset / 2;
        double lr = hardware.LeftJoystick.getX() + axis0Offset / 2;
        drive.SetLeft(fb + -lr);
        drive.SetRight(fb + lr);
    }

}