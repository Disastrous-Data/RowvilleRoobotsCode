package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

// Main class for controlling the robot, all robot functions should use this.
public class TankDrive {

    private MotorController[] LeftMotors;
    private MotorController[] RightMotors;
    private MotorController[] WinchMotors;
    private MotorController[] ArmMotors;

    public Pnumatics Pnumatics;
    public LEDManager Leds;
    public Joystick Controller;

    private Hardware Hardware;

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
        WinchMotors = new MotorController[] {
            hardware.WinchLeft,
            hardware.WinchRight
        };
        ArmMotors = new MotorController[] {
            hardware.Arm
        };
        Pnumatics = new Pnumatics();
        Leds = new LEDManager(hardware);
        Controller = hardware.LeftJoystick;
    }

    public void Update(HardwareStates states) {

        // Drive
        SetLeftDrive(states.LeftDriveMotors);
        SetRightDrive(states.RightDriveMotors);

        // Winch
        SetWinchPower(states.WinchMotors);

        // Arm
        SetArmPower(states.ArmMotors);

        //Claw Piston
        //SetClawPiston(states.ClawPiston);
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

    public void SetWinchPower(double s) {
        for (MotorController c : WinchMotors) {
            c.set(s);
        }
    }

    public void SetArmPower(double s) {
        for (MotorController c : ArmMotors) {
            c.set(s);
        }
    }

    public void SetClawPiston(boolean s) {
        Pnumatics.SetState(s);
    }

    public void Stop() {
        Hardware.Reset();
    }
    
}
