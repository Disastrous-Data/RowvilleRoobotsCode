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
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.*;

public class Hardware {


    // ==========================
    //         Drive Motors
    // ==========================
    public CANSparkMax RightMotor1;
    public CANSparkMax RightMotor2;

    public CANSparkMax LeftMotor1;
    public CANSparkMax LeftMotor2;

    // ==========================
    //        Other Motors
    // ==========================
    public WPI_TalonSRX TopIntakeMotor;
    public WPI_TalonSRX BottomIntakeMotor;

    public CANSparkMax RollerClaw;

    // ==========================
    //         Controls
    // ==========================
    public Joystick Controller;
    public Joystick Slider;

    // ==========================
    //         Pneumatics
    // ==========================
    public Compressor AirCompressor;
    public DoubleSolenoid Solenoid;

    // ==========================
    //           Sensors
    // ==========================
    public DigitalInput LimitSwitch;
    public AHRS NavX;

    public void Init() {  // Arm: 2,4 Intake: 5,7
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed);
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);

        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);

        TopIntakeMotor = new WPI_TalonSRX(7);
        BottomIntakeMotor = new WPI_TalonSRX(5);

        RollerClaw = new CANSparkMax(8, MotorType.kBrushed);

        // Controller stuff
        Controller = new Joystick(0);
        Slider = new Joystick(3);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);

        RightMotor2.setIdleMode(IdleMode.kBrake);
        RightMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor2.setIdleMode(IdleMode.kBrake);

        //AirCompressor = new Compressor(PneumaticsModuleType.REVPH);
        //Solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);

        //LimitSwitch = new DigitalInput(9);
        NavX = new AHRS();
    }

    public void Reset() {
        RightMotor1.set(0);
        RightMotor2.set(0);
        LeftMotor1.set(0);
        LeftMotor2.set(0);
    }
}
