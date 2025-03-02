/*
Hardware.java
Written by CoPokBl

This contains all the hardware definitions for the robot.
An instance of it can be used to access all the hardware.
If any hardware is changed for added it should be added here.

Where possible you shouldn't use anything here directly, instead use
a TankDrive instance which is a safer approach and allows for
checks to be made on the values being set to the motors.
*/

package com.disastrousdata;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Hardware {


    // ==========================
    //         Drive Motors
    // ==========================
    public SparkMax RightMotor1;
    public SparkMax RightMotor2;

    public SparkMax LeftMotor1;
    public SparkMax LeftMotor2;

    // ==========================
    //        Other Motors
    // ==========================
    public WPI_TalonSRX TopIntakeMotor;
    public WPI_TalonSRX BottomIntakeMotor;

    public WPI_TalonSRX RollerClaw;
    public SparkMax GroundIntakeSwing;
    public WPI_TalonSRX GroundIntakeSpin;

    // ==========================
    //         Controls
    // ==========================
    public Joystick Controller;
    public Joystick Slider;

    // ==========================
    //         Pneumatics
    // ==========================
    //public Compressor AirCompressor;
    public DoubleSolenoid Solenoid;

    // ==========================
    //           Sensors
    // ==========================
    //public DigitalInput LimitSwitch;
    // public AHRS NavX;
    public DutyCycleEncoder IntakeEncoder;

    public void Init() {  // Arm: 2,4 Intake: 5,7, RollerCLaw: 4, GroundIntake: 5, 2,
        RightMotor1 = new SparkMax(1, MotorType.kBrushed);
        RightMotor2 = new SparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new SparkMax(3, MotorType.kBrushed);
        LeftMotor2 = new SparkMax(4, MotorType.kBrushed);

        // Shooter
        TopIntakeMotor = new WPI_TalonSRX(7);
        BottomIntakeMotor = new WPI_TalonSRX(5);

        RollerClaw = new WPI_TalonSRX(4);

        // Ground intake (not shooter)
        GroundIntakeSwing = new SparkMax(5, MotorType.kBrushless);
       // GroundIntakeSwing.setSmartCurrentLimit(20);
        // GroundIntakeSwing.setIdleMode(IdleMode.kBrake);

        GroundIntakeSpin = new WPI_TalonSRX(2);

        // Controller stuff
        Controller = new Joystick(0);
        Slider = new Joystick(3);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);

        // RightMotor2.setIdleMode(IdleMode.kBrake);
        // RightMotor1.setIdleMode(IdleMode.kBrake);
        // LeftMotor1.setIdleMode(IdleMode.kBrake);
        // LeftMotor2.setIdleMode(IdleMode.kBrake);

        //AirCompressor = new Compressor(PneumaticsModuleType.REVPH);
        //Solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);

        //LimitSwitch = new DigitalInput(9);
        // NavX = new AHRS();
        IntakeEncoder = new DutyCycleEncoder(0);
    }

    public void Reset() {
        RightMotor1.set(0);
        RightMotor2.set(0);
        LeftMotor1.set(0);
        LeftMotor2.set(0);
    }
}
