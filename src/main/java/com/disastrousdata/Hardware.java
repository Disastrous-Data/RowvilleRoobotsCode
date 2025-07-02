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

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class Hardware {


    // ==========================
    //         Drive Motors
    // ==========================
    public SparkMax rightMotor1;
    public SparkMax rightMotor2;

    public SparkMax leftMotor1;
    public SparkMax leftMotor2;

    // ==========================
    //        Other Motors
    // ==========================
    public SparkMax intake;
    public WPI_TalonSRX armMotor;

    // ==========================
    //         Controls
    // ==========================
    public Joystick controller;
    public Joystick slider;

    public void init() {

        // Drive
        rightMotor1 = new SparkMax(1, MotorType.kBrushed);
        rightMotor2 = new SparkMax(2, MotorType.kBrushed);
        leftMotor1 = new SparkMax(3, MotorType.kBrushed);
        leftMotor2 = new SparkMax(4, MotorType.kBrushed);

        // Intake
        intake = new SparkMax(5, MotorType.kBrushed);  // Spinny thing

        // Front flap
        armMotor = new WPI_TalonSRX(7);

        // Controller stuff
        controller = new Joystick(0);
        slider = new Joystick(3);

        // Use new SparkMaxConfig type to configure parameters
        SparkBaseConfig driveConfig = new SparkMaxConfig().idleMode(SparkBaseConfig.IdleMode.kBrake);
        rightMotor1.configure(driveConfig.inverted(false), SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
        rightMotor2.configure(driveConfig.inverted(false), SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
        leftMotor1.configure(driveConfig.inverted(true), SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
        leftMotor2.configure(driveConfig.inverted(true), SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    /// <summary>
    /// Stops all motors by setting their power to 0.
    /// </summary>
    public void reset() {
        rightMotor1.set(0);
        rightMotor2.set(0);
        leftMotor1.set(0);
        leftMotor2.set(0);
    }
}
