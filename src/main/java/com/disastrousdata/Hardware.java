package com.disastrousdata;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
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
    //         Controls
    // ==========================
    public Joystick LeftJoystick;
    public Joystick Slider;

    // ==========================
    //         Pnumatics
    // ==========================
    public Compressor AirCompressor;
    public DoubleSolenoid Solenoid;

    // ==========================
    //           Sensors
    // ==========================
    public DigitalInput LimitSwitch;
    public AHRS NavX;

    public void Init() {
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed);
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);
        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);

        LeftJoystick = new Joystick(0);
        Slider = new Joystick(3);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);

        RightMotor2.setIdleMode(IdleMode.kBrake);
        RightMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor2.setIdleMode(IdleMode.kBrake);

        AirCompressor = new Compressor(PneumaticsModuleType.REVPH);
        Solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);

        LimitSwitch = new DigitalInput(9);
        NavX = new AHRS();
    }

    public void Reset() {
        RightMotor1.set(0);
        RightMotor2.set(0);
        LeftMotor1.set(0);
        LeftMotor2.set(0);
    }
}
