package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
  private Hardware hardware = new Hardware();
  private TankDrive drive = new TankDrive();
  private Teleop teleop = new Teleop();
  private Auto auto = new Auto();

  private double autoStartTime = 0;

  @Override
  public void robotInit() {
    hardware.Init();
    drive.Init(hardware);
    auto.Init();
    //Starts camera server for usb camera plugged into RoboRIO, pushes to dashboard(s).
    CameraServer.startAutomaticCapture();  
  }

  @Override
  public void teleopPeriodic() {
    teleop.Invoke(drive);
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
