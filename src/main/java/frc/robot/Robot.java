// Copyright (c) Rowville Roobots Team
// Contributors (In order of contribution): CoPokBl

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
  public boolean TestValue1;
  public String TestValue2;
  public Integer TestValue3;
  private Hardware hardware = new Hardware();
  private TankDrive drive = new TankDrive();
  private Teleop teleop = new Teleop();

  @Override
  public void robotInit() {
    hardware.Init();
    drive.Init(hardware);
  }

  @Override
  public void teleopPeriodic() { teleop.Invoke(drive, hardware); }

}
