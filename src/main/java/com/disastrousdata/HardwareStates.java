/*
HardwareStates.java
Written by CoPokBl

This is a schema for the current state of the hardware.
An instance of this can be passed and modified by Auto or Teleop code
and then applied to the hardware after.

Add any new hardware to this class.
*/

package com.disastrousdata;

public class HardwareStates {
    private double leftDriveMotors = 0;
    private double rightDriveMotors = 0;

    public double getLeftDriveMotors() {
        return leftDriveMotors;
    }

    public void setLeftDriveMotors(double leftDriveMotors) {
        this.leftDriveMotors = leftDriveMotors;
    }

    public double getRightDriveMotors() {
        return rightDriveMotors;
    }

    public void setRightDriveMotors(double rightDriveMotors) {
        this.rightDriveMotors = rightDriveMotors;
    }
}
