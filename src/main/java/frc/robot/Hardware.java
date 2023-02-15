package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Hardware {
    
    public MotorController RightMotor1;
    public MotorController RightMotor2;

    public MotorController LeftMotor1;
    public MotorController LeftMotor2;

    public MotorController Arm;

    public Joystick LeftJoystick;
    public Joystick Slider;

    public void Init() {
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed);
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);
        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);

        Arm = new WPI_TalonSRX(5);
        LeftJoystick = new Joystick(0);
        Slider = new Joystick(3);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);
    }

}
