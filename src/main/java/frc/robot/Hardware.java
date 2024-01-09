package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

public class Hardware {
    
    public CANSparkMax RightMotor1;
    public CANSparkMax RightMotor2;

    public CANSparkMax LeftMotor1;
    public CANSparkMax LeftMotor2;

    public CANSparkMax Intake;

    public WPI_TalonSRX ArmInOut;

    public WPI_TalonSRX WinchLeft;
    public WPI_TalonSRX WinchRight;

    public Joystick LeftJoystick;
    public Joystick Slider;

    public Compressor AirCompressor;

    public AddressableLED m_led;
    public AddressableLEDBuffer m_ledBuffer;

    public DoubleSolenoid Solenoid;

    public DigitalInput ArmLimitSwitch;

    public AHRS NavX;

    public void Init() {
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed);
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);
        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);

        Intake = new CANSparkMax(5, MotorType.kBrushless);
        LeftJoystick = new Joystick(0);
        Slider = new Joystick(3);

        ArmInOut = new WPI_TalonSRX(5);

        WinchRight = new WPI_TalonSRX(6);
        WinchLeft = new WPI_TalonSRX(7);

        WinchRight.setInverted(true);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);

        RightMotor2.setIdleMode(IdleMode.kBrake);
        RightMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor1.setIdleMode(IdleMode.kBrake);
        LeftMotor2.setIdleMode(IdleMode.kBrake);

        AirCompressor = new Compressor(PneumaticsModuleType.REVPH);

        //LED's
        m_led = new AddressableLED(0);
        m_ledBuffer = new AddressableLEDBuffer(62);  // 60 LEDs
        m_led.setLength(m_ledBuffer.getLength());
        m_led.setData(m_ledBuffer);
        m_led.start();

        Solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);

        ArmLimitSwitch = new DigitalInput(9);

        NavX = new AHRS();
    }

    public void Reset() {
        RightMotor1.set(0);
        RightMotor2.set(0);
        LeftMotor1.set(0);
        LeftMotor2.set(0);
        Intake.set(0);
        WinchLeft.set(0);
        WinchRight.set(0);
    }
}
