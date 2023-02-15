// Copyright (c) Rowville Roobots Team
// Contributors (In order of contribution): CoPokBl

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  public boolean TestValue1;
  public String TestValue2;
  public Integer TestValue3;
  private Hardware hardware = new Hardware();
  private TankDrive drive = new TankDrive();
  private Teleop teleop = new Teleop();
  private Auto auto = new Auto();

  @Override
  public void robotInit() {
    hardware.Init();
    drive.Init(hardware);
    auto.Init();
  }

  @Override
  public void teleopPeriodic() {
    teleop.Invoke(drive, hardware);
  }

  @Override
  public void autonomousPeriodic() {
    AutoState state = new AutoState();
    state.hardware = hardware;
    state.drive = drive;
    state.timeElapsed = 0;
    auto.Invoke(state);
  }

}
