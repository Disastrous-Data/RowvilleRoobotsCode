package frc.robot;

public class Teleop {

    private boolean stop = false;
    private double axis0Offset = -0.02;
    private double axis1Offset = -0.4;

    public void Invoke(TankDrive drive, Hardware hardware) {
        double sb = hardware.LeftJoystick.getRawAxis(0);
        double usb = hardware.LeftJoystick.getRawAxis(1);
        if (sb == 1) stop = true;
        if (usb == 1) stop = false;

        if (hardware.LeftJoystick.getRawButton(5)) {
            hardware.Arm.set(1);
        }

        if (hardware.LeftJoystick.getRawButton(6)) {
            hardware.Arm.set(-1);
        }

        if (stop) return;
        double fb = hardware.LeftJoystick.getY() + axis1Offset / 2;
        double lr = hardware.LeftJoystick.getX() + axis0Offset / 2;
        drive.SetLeft(fb + -lr);
        drive.SetRight(fb + lr);
    }

}