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

    private MotorController[] LeftMotors;
    private MotorController[] RightMotors;

    public MotorController IntakeTop;
    public MotorController IntakeBottom;

    public MotorController Arm;
    public MotorController Claw;

    public Pneumatics Pneumatics;
    public Joystick Controller;

    public Hardware Hardware;

    // Hardware should be initialized before this is called. This is called in Hardware.java.
    public void Init(Hardware hardware) {
        Hardware = hardware;
        LeftMotors = new MotorController[] {
            hardware.LeftMotor1,
            hardware.LeftMotor2
        };
        RightMotors = new MotorController[] {
            hardware.RightMotor1,
            hardware.RightMotor2
        };

        IntakeTop = hardware.TopIntakeMotor;
        IntakeBottom = hardware.BottomIntakeMotor;

        Arm = hardware.ArmMotor;
        Claw = hardware.ClawMotor;

        Pneumatics = new Pneumatics(hardware.Solenoid);
        Controller = hardware.Controller;
    }

    public void Update(HardwareStates states) {
        // Drive
        SetLeftDrive(states.LeftDriveMotors);
        SetRightDrive(states.RightDriveMotors);
    }

    public void SetLeftDrive(double s) {
        for (MotorController c : LeftMotors) {
            c.set(s);
        }
    }

    public void SetRightDrive(double s) {
        for (MotorController c : RightMotors) {
            c.set(s);
        }
    }

    public enum IntakeMode {
        OFF,
        INTAKE,
        CHARGE,
        SHOOT
    }

    public void SetIntakeMode(IntakeMode mode) {
        switch (mode) {
            case OFF:
                IntakeTop.set(0);
                IntakeBottom.set(0);
                Dash.set("intakemode", "off");
                break;
            case INTAKE:
                IntakeTop.set(-0.3);
                IntakeBottom.set(-0.3);
                Dash.set("intakemode", "intake");
                break;
            case CHARGE:
                IntakeTop.set(1);
                IntakeBottom.set(0);
                Dash.set("intakemode", "charge");
                break;
            case SHOOT:
                IntakeTop.set(1);
                IntakeBottom.set(1);
                Dash.set("intakemode", "shoot");
                break;
        }
    }

    public void setClawPower(double s) {
        Claw.set(s);
    }

    public void setArmPower(double s) {
        Arm.set(s);
    }

    @SuppressWarnings("unused")  // May be used in future
    public void Stop() {
        Hardware.Reset();
    }
    
}
