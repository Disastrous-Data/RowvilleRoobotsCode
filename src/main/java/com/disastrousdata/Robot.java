/*
Robot.java
Written by CoPokBl

This contains the framework for executing auto and teleop code.
You probably shouldn't modify this file.
*/

package com.disastrousdata;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {
  private final Hardware hardware = new Hardware();
  private final TankDrive drive = new TankDrive();
  private final Teleop teleop = new Teleop();
  private final Auto auto = new Auto();

  private double autoStartTime = 0;
  private double teleopStartTime = 0;

    @Override
  public void robotInit() {

    // Load all hardware and subsystems.
    hardware.Init();
    drive.Init(hardware);
    auto.Init();

    // Starts camera server for usb camera plugged into RoboRIO, pushes to dashboard(s).
    UsbCamera camera1 = CameraServer.startAutomaticCapture(0);
    camera1.setFPS(60);
  }

  @Override
  public void teleopInit() {
    teleopStartTime = Timer.getFPGATimestamp();
  }

  @Override
  public void teleopPeriodic() {
    teleop.Invoke(drive, Timer.getFPGATimestamp() - teleopStartTime);
  }

  @Override
  public void autonomousInit() {
    autoStartTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    AutoState state = new AutoState();
    state.drive = drive;
    state.timeElapsed = Timer.getFPGATimestamp() - autoStartTime;
    auto.Invoke(state);
  }

}
