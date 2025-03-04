/*
TankDrive.java
Written by CoPokBl

This is a wrapper class for dealing with hardware.
It is a more abstract way of dealing with hardware, and it makes code easier to read.
It also allows for adding checks to hardware such as not letting an arm go too far.
Add more hardware to this class as needed.
*/

package com.disastrousdata;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

// Main class for controlling the robot, all robot functions should use this.
public class TankDrive {

    private MotorController[] leftMotors;
    private MotorController[] rightMotors;

    public MotorController intake;

    public Joystick controller;

    public Hardware hardware;

    // Hardware should be initialized before this is called. This is called in Hardware.java.
    public void init(Hardware hardware) {
        this.hardware = hardware;
        leftMotors = new MotorController[] {
            hardware.leftMotor1,
            hardware.leftMotor2
        };
        rightMotors = new MotorController[] {
            hardware.rightMotor1,
            hardware.rightMotor2
        };

        intake = hardware.intake;
        controller = hardware.controller;
    }

    public void update(HardwareStates states) {
        setLeftDrive(states.getLeftDriveMotors());
        setRightDrive(states.getRightDriveMotors());
    }

    public void setLeftDrive(double s) {
        for (MotorController c : leftMotors) {
            c.set(s);
        }
    }

    public void setRightDrive(double s) {
        for (MotorController c : rightMotors) {
            c.set(s);
        }
    }

    public void setIntakePower(double s) {
        intake.set(s);
    }

    @SuppressWarnings("unused")  // May be used in future
    public void stop() {
        hardware.reset();
    }
    
}
