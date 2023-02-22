// Copyright (c) Rowville Roobots Team
// Contributors (In order of contribution): CoPokBl, Camo

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

// This class should not be modified
public final class Main {
  private Main() {}

  // This should only be changed if the Robot class is renamed or moved
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
