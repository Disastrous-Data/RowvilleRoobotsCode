// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// 1&2 are right
// 3&4 are left

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  //private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  //private Joystick m_rightStick;

  private MotorController rm1;
  private MotorController rm2;

  private MotorController lm1;
  private MotorController lm2;

  private boolean stop = false;

  private void setLeft(double s) {
    lm1.set(s);
    lm2.set(s);
  }

  private void setRight(double s) {
    rm1.set(s);
    rm2.set(s);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
  
    rm1 = new CANSparkMax(1, MotorType.kBrushed);
    rm2 = new CANSparkMax(2, MotorType.kBrushed);
    lm1 = new CANSparkMax(3, MotorType.kBrushed);
    lm2 = new CANSparkMax(4, MotorType.kBrushed);
    m_leftStick = new Joystick(0);

    rm2.setInverted(true);
    rm1.setInverted(true);
    lm1.setInverted(false);
    lm2.setInverted(false);

    //m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);
  }

  @Override
  public void teleopPeriodic() {

    double sb = m_leftStick.getRawAxis(0);
    double usb = m_leftStick.getRawAxis(1);
    if (sb == 1) stop = true;
    if (usb == 1) stop = false;

    if (m_leftStick.getRawButton(5)) {
      // TODO: Make thing
    }

    if (m_leftStick.getRawButton(6)) {
      // TODO: Make other thing
    }

    if (stop) return;
    double fb = m_leftStick.getY() / 2;
    double lr = m_leftStick.getX() / 2;
    setLeft(fb + lr);
    setRight(fb + -lr);
  }

}
