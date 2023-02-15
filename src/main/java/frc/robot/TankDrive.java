package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class TankDrive {

    private MotorController[] LeftMotors;
    public MotorController[] RightMotors;

    public Pnumatics Pnumatics;

    public void Init(Hardware hardware) {
        LeftMotors = new MotorController[] {
            hardware.LeftMotor1,
            hardware.LeftMotor2
        };
        RightMotors = new MotorController[] {
            hardware.RightMotor1,
            hardware.RightMotor2
        };
        Pnumatics = new Pnumatics();
    }

    public void SetLeft(double s) {
        for (MotorController c : LeftMotors) {
            c.set(s);
        }
    }

    public void SetRight(double s) {
        for (MotorController c : RightMotors) {
            c.set(s);
        }
    }
    
}
