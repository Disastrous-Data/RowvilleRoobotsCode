package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class MotorDefinitions {

    // Drive:
    WPI_VictorSPX driveLeftA = new WPI_VictorSPX(3);
    WPI_VictorSPX driveLeftB = new WPI_VictorSPX(4);
    WPI_VictorSPX driveRightA = new WPI_VictorSPX(1);
    WPI_VictorSPX driveRightB = new WPI_VictorSPX(2);

    // Other:
    VictorSPX intake = new VictorSPX(7);
    VictorSPX arm = new VictorSPX(8);
    Spark door = new Spark(0);
    Joystick driverController = new Joystick(0);

}
