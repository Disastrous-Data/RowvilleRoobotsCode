package frc.robot;

@interface AutonomousInit { }
@interface AutonomousPeriodic { }
@interface TeleopInit { }
@interface TeleopPeriodic { }

public class EventInvoker {

    public static void AutonomousInit() {
        Autonomous.AutonomousInit();  // TODO: Make this good
    }

}
