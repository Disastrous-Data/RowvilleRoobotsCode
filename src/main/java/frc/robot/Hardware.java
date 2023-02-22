package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Hardware {
    
    public MotorController RightMotor1;
    public MotorController RightMotor2;

    public MotorController LeftMotor1;
    public MotorController LeftMotor2;

    public WPI_TalonSRX Arm;

    public WPI_TalonSRX WinchLeft;
    public WPI_TalonSRX WinchRight;

    public Joystick LeftJoystick;
    public Joystick Slider;

    public Compressor AirCompressor;

    public AddressableLED m_led;
    public AddressableLEDBuffer m_ledBuffer;

    public void Init() {
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed);
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);
        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);

        Arm = new WPI_TalonSRX(5);
        LeftJoystick = new Joystick(0);
        Slider = new Joystick(3);

        WinchRight = new WPI_TalonSRX(6);
        WinchLeft = new WPI_TalonSRX(7);

        WinchRight.setInverted(true);

        RightMotor2.setInverted(true);
        RightMotor1.setInverted(true);
        LeftMotor1.setInverted(false);
        LeftMotor2.setInverted(false);

        AirCompressor = new Compressor(PneumaticsModuleType.REVPH);

        //LED's
        m_led = new AddressableLED(9);
        m_ledBuffer = new AddressableLEDBuffer(60);  // 60 LEDs
        m_led.setLength(m_ledBuffer.getLength());
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    public void Reset() {
        RightMotor1.set(0);
        RightMotor2.set(0);
        LeftMotor1.set(0);
        LeftMotor2.set(0);
        Arm.set(0);
        WinchLeft.set(0);
        WinchRight.set(0);
    }
}
