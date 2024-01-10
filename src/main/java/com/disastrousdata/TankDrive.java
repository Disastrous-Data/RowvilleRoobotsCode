package com.disastrousdata;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

// Main class for controlling the robot, all robot functions should use this.
public class TankDrive {

    private MotorController[] LeftMotors;
    private MotorController[] RightMotors;

    public Pnumatics Pnumatics;
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
        Pnumatics = new Pnumatics(hardware.Solenoid);
        Controller = hardware.LeftJoystick;
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

    @SuppressWarnings("unused")  // May be used in future
    public void Stop() {
        Hardware.Reset();
    }
    
}
