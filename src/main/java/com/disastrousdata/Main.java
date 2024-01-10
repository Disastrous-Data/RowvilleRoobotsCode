// Copyright (c) Disastrous Data
// Contributors (In order of contribution): CoPokBl
// Before making changes please read README.md in the project root.

package com.disastrousdata;

import edu.wpi.first.wpilibj.RobotBase;

// This class should never be changed because it just starts the robot.
// All actual robot code is in Robot.java
public final class Main {
  private Main() { }
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
