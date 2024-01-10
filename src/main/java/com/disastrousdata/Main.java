// Copyright (c) Disastrous Data
// Contributors (In order of contribution): CoPokBl, Camo

package com.disastrousdata;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {}
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
