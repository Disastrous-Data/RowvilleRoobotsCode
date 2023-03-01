package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {
  private Hardware hardware = new Hardware();
  private TankDrive drive = new TankDrive();
  private Teleop teleop = new Teleop();
  private Auto auto = new Auto();

  private double autoStartTime = 0;

  private UsbCamera camera1;

  @Override
  public void robotInit() {
    hardware.Init();
    drive.Init(hardware);
    auto.Init();
    // Starts camera server for usb camera plugged into RoboRIO, pushes to dashboard(s).
    // CameraServer.startAutomaticCapture();  
    camera1 = CameraServer.startAutomaticCapture(0);
    camera1.setFPS(60);
  }

  @Override
  public void teleopInit() {
    drive.SetClawPiston(false);
  }

  @Override
  public void teleopPeriodic() {
    teleop.Invoke(drive);
  }

  @Override
  public void autonomousInit() {
    autoStartTime = Timer.getFPGATimestamp();
    drive.SetClawPiston(true);
  }

  @Override
  public void autonomousPeriodic() {
    AutoState state = new AutoState();
    state.drive = drive;
    state.timeElapsed = Timer.getFPGATimestamp() - autoStartTime;
    auto.Invoke(state);
  }

}
