/*
  2022 everybot code
  written by carson graf 
  don't email me, @ me on discord
*/

/*
  This is catastrophically poorly written code for the sake of being easy to follow
  If you know what the word "refactor" means, you should refactor this code
*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
//import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMax.IdleMode;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Button;

public class Robot extends TimedRobot {


  // ======== Hardware definitions ========
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

  // ======== Constants ========

  // ======== State Variables ========
  double autoStart = 0;
  boolean goForAuto = false;



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    driveLeftA.setInverted(false);
    driveLeftB.setInverted(false);
    driveRightA.setInverted(true);
    driveRightB.setInverted(true);

    arm.setInverted(false);

    // Add a control on the dashboard to turn off auto if needed
    SmartDashboard.putBoolean("Go For Auto", false);
    goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }

  @Override
  public void autonomousInit() {
    // Get auto start time to time events
    autoStart = Timer.getFPGATimestamp();

    // Check dashboard icon to ensure good to do auto
    goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    if (!goForAuto) {
      // Do run auto code if we're not supposed to
      return;
    }

    // Get time since start of auto
    double autoTimeElapsed = Timer.getFPGATimestamp() - autoStart;

    // Variables for motor control
    // ONLY MODIFY THESE, DO NOT SET POWER DIRECTLY
    double driveLeftPower = 0;
    double driveRightPower = 0;
    double doorPower = 0;


    // ======== Autonomous Timed Events ========
    if (autoTimeElapsed < 2) {  // Move forward for 2 seconds (from time = 0 to time = 2)
      driveLeftPower = 0.3;
      driveRightPower = 0.3;
    } else if (autoTimeElapsed < 2.5) {  // Spin for 0.5 seconds (from time = 2 to time = 2.5)
      driveRightPower = -0.5;
      driveLeftPower = 0.5;
    }

    if (autoTimeElapsed < 9.9 && autoTimeElapsed >= 8.6) {
      // Release ball
      doorPower = 0.8;
    }


    // ======== Set Motor Power (DO NOT MODIFY) ========
    driveLeftA.set(driveLeftPower);
    driveLeftB.set(driveLeftPower);
    driveRightA.set(driveRightPower);
    driveRightB.set(driveRightPower);
    door.set(doorPower);
  }

  @Override
  public void teleopInit() {}



  // ======== Teleop Variables ========
  boolean goFast = false;
  boolean balls = false;
  boolean isBalling = false;
  double pressCooldownTimeStamp = 0;

  /** Called periodically during manual control mode. */
  @Override
  public void teleopPeriodic() {
    // Get joystick inputs
    double forward = -driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);

    // Declare Power Settings
    double driveLeftPower = 0;
    double driveRightPower = 0;
    double doorPower = 0;

    if (isBalling) {
      // Do hatch stuff
      if (Timer.getFPGATimestamp() - pressCooldownTimeStamp > 1) {
        isBalling = false;
      }
      if (balls) {
        // Hatch is down
        doorPower = 0.8;
      } else {
        // Hatch is up
        doorPower = -1;
      }
     }

    // Control logic
    if (driverController.getRawButton(6)) {
      // Sprint
      driveLeftPower = 0.9;
      driveRightPower = 0.9;
    } else if (driverController.getRawButton(5)) {
      // Spin
      driveLeftPower = -1;
      driveRightPower = 1;
    } else if (
      driverController.getRawButton(3) &&
    Timer.getFPGATimestamp() - pressCooldownTimeStamp > 1 &&
    !isBalling) {
      // Hatch toggle
      pressCooldownTimeStamp = Timer.getFPGATimestamp();
      isBalling = !isBalling;
      balls = !balls;
    }
    else {
      // Normal joystick movement
      driveLeftPower = (forward - turn) * 1;
      driveRightPower = (forward + turn) * 1;
    }





    driveLeftA.set(driveLeftPower);
    driveLeftB.set(driveLeftPower);
    driveRightA.set(driveRightPower);
    driveRightB.set(driveRightPower);

    door.set(doorPower);

    //Door controls
    if (!isBalling) {
      if(driverController.getRawButton(1)){
        door.set(1);
      }
      else if(driverController.getRawButton(2)){
        door.set(-1);
      }
      else{
        door.set(0);
      }
    }


    // Arm Controls
    if(driverController.getRawButton(3)){
      arm.set(VictorSPXControlMode.PercentOutput, -1);;
    }
    else if(driverController.getRawButton(5)){
      arm.set(VictorSPXControlMode.PercentOutput, 1);
    }
    else{
      arm.set(VictorSPXControlMode.PercentOutput, 0);
    }

  }

  @Override
  public void disabledInit() {
    // On disable turn off everything
    // Done to solve issue with motors "remembering" previous setpoints after reenable
    driveLeftA.set(0);
    driveLeftB.set(0);
    driveRightA.set(0);
    driveRightB.set(0);
    // arm.set(0);
    intake.set(ControlMode.PercentOutput, 0);
  }
    
}