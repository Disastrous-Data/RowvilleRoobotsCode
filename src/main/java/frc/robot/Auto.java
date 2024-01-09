package frc.robot;

// In auto we want to do the following
// - Extend arm
// - Place cone on a base
// - Move arm away
// - Go back out of the line

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

    private List<TimedEvent> events = new ArrayList<>();
    private List<TimedEvent> oneOffEvents = new ArrayList<>();

    private boolean autoBalanceXMode;
    private boolean autoBalanceYMode;

    private ArrayList<Double> leftSpeeds = new ArrayList<>();
    private ArrayList<Double> Pitches = new ArrayList<>();

    // Add timed events using registerTimedEvent only in Init().
    public void Init() {
        // Register timed events

        // START EXIT HOME

        // Back out of home area
        // registerTimedEvent(0, 2, new Command() {
        //     public HardwareStates Execute(
        //         TankDrive drive, HardwareStates states) {
        //         states.LeftDriveMotors = 0.6;
        //         states.RightDriveMotors = 0.6;
        //         return states;
        //     }
        // });

        // START DOCk

    
        // Go onto the start of the charge station
        registerTimedEvent(0,1, new Command() {
            public HardwareStates Execute(
                TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.6;
                states.RightDriveMotors = 0.6;
                return states;
            }
        });

        // Go over the charge station and out of home zone
        registerTimedEvent(1,4, new Command() {
            public HardwareStates Execute(
                TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.35;
                states.RightDriveMotors = 0.35;
                return states;
            }
        });

        // Back back onto the charge station
        registerTimedEvent(4, 6.6, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = -0.4;
                states.RightDriveMotors = -0.4;
                return states;
            }
        });

        // Balance
        registerTimedEvent(6.6, 25, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                double xAxisRate = 0;
                double yAxisRate = 0;

                double pitchAngleDegrees = drive.Hardware.NavX.getPitch();
                double rollAngleDegrees = drive.Hardware.NavX.getRoll();

                SmartDashboard.putNumber("roll", rollAngleDegrees);
                SmartDashboard.putNumber("pitch", pitchAngleDegrees);

                Pitches.add(pitchAngleDegrees);
                SmartDashboard.putNumberArray("pitches", Pitches.stream().mapToDouble(o -> ((Number) o).doubleValue()).toArray());

                if (!autoBalanceXMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
                    autoBalanceXMode = true;
                } else if (autoBalanceXMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
                    autoBalanceXMode = false;
                }
                if (!autoBalanceYMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
                    autoBalanceYMode = true;
                } else if (autoBalanceYMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
                    autoBalanceYMode = false;
                }

                // Control drive system automatically,
                // driving in reverse direction of pitch/roll angle,
                // with a magnitude based upon the angle

                if (autoBalanceXMode) {
                    SmartDashboard.putBoolean("x Balance Enabled", autoBalanceXMode);
                    double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
                    xAxisRate = Math.sin(pitchAngleRadians) * -1;
                } else {
                    states.Intake = 0.5;
                }
                if (autoBalanceYMode) {
                    SmartDashboard.putBoolean("y Balance Enabled", autoBalanceYMode);
                    double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
                    yAxisRate = Math.sin(rollAngleRadians) * -1;
                } else {
                    states.Intake = 0.5;
                }

                try {
                    //myRobot.driveCartesian(xAxisRate, yAxisRate, stick.getTwist(), 0);
                    double leftAxisRate = yAxisRate + xAxisRate;
                    double rightAxisRate = yAxisRate - xAxisRate;
                    SmartDashboard.putNumber("yAxisRate", yAxisRate);
                    SmartDashboard.putNumber("xAxisRate", xAxisRate);
                    SmartDashboard.putNumber("rightAxisRate", rightAxisRate);
                    SmartDashboard.putNumber("leftAxisRate", leftAxisRate);
                    leftSpeeds.add(leftAxisRate);
                    // SmartDashboard.putNumberArray("leftAxisRate", leftSpeeds.stream().mapToDouble(o -> ((Number) o).doubleValue()).toArray());
                    states.LeftDriveMotors = (leftAxisRate*-1);
                    states.RightDriveMotors = (leftAxisRate*-1);
                } catch (RuntimeException ex) {
                    // IDK MAN
                    states.LeftDriveMotors = -0.3;
                    states.RightDriveMotors = 0.3;
                }
                return states;
            }
        });

    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead.

    static final double kOffBalanceAngleThresholdDegrees = 0;
    static final double kOonBalanceAngleThresholdDegrees = 0;

    public void Invoke(AutoState state) {

        // double xAxisRate = 0;
        // double yAxisRate = 0;

        // double pitchAngleDegrees = state.drive.Hardware.NavX.getPitch();
        // double rollAngleDegrees = state.drive.Hardware.NavX.getRoll();

        // SmartDashboard.putNumber("roll", rollAngleDegrees);
        // SmartDashboard.putNumber("pitch", pitchAngleDegrees);

        // if (!autoBalanceXMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
        //     autoBalanceXMode = true;
        // } else if (autoBalanceXMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
        //     autoBalanceXMode = false;
        // }
        // if (!autoBalanceYMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
        //     autoBalanceYMode = true;
        // } else if (autoBalanceYMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
        //     autoBalanceYMode = false;
        // }

        // // Control drive system automatically,
        // // driving in reverse direction of pitch/roll angle,
        // // with a magnitude based upon the angle

        // if (autoBalanceXMode) {
        //     double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
        //     xAxisRate = Math.sin(pitchAngleRadians) * -1;
        // }
        // if (autoBalanceYMode) {
        //     double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
        //     yAxisRate = Math.sin(rollAngleRadians) * -1;
        // }

        // try {
        //     //myRobot.driveCartesian(xAxisRate, yAxisRate, stick.getTwist(), 0);
        //     HardwareStates states = new HardwareStates();
        //     double leftAxisRate = yAxisRate + xAxisRate;
        //     double rightAxisRate = yAxisRate - xAxisRate;
        //     SmartDashboard.putNumber("yAxisRate", yAxisRate);
        //     SmartDashboard.putNumber("xAxisRate", xAxisRate);
        //     SmartDashboard.putNumber("rightAxisRate", rightAxisRate);
        //     SmartDashboard.putNumber("leftAxisRate", leftAxisRate);
        //     states.LeftDriveMotors = (leftAxisRate);
        //     states.RightDriveMotors = (leftAxisRate);
        //     state.drive.Update(states);
        // } catch (RuntimeException ex) {
        //     // IDK MAN
        // }
        // return;

        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);
        boolean didDoSomething = false;
        for (TimedEvent event : events) {
            if (state.timeElapsed >= event.StartTime && state.timeElapsed <= event.EndTime) {
                HardwareStates states = event.Method.Execute(state.drive, new HardwareStates());
                
                // // ARM LIMIT SWITCH
                // if (state.drive.Hardware.ArmLimitSwitch.get() && states.ArmUpDownMotors < 0) {
                //     states.ArmUpDownMotors = 0;
                // }

                state.drive.Update(states);
                didDoSomething = true;
            }
        }
        for (TimedEvent oneOffEvent : oneOffEvents) {
            if (oneOffEvent.StartTime <= state.timeElapsed) {
                HardwareStates states = oneOffEvent.Method.Execute(state.drive, new HardwareStates());
                
                // ARM LIMIT SWITCH
                // if (state.drive.Hardware.ArmLimitSwitch.get() && states.ArmUpDownMotors < 0) {
                //     states.ArmUpDownMotors = 0;
                // }

                state.drive.Update(states);
                oneOffEvents.remove(oneOffEvent);
                didDoSomething = true;
            }
        }
        if (!didDoSomething) {
            state.drive.Update(new HardwareStates());
        }
    }

    private void registerTimedEvent(double start, double end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

    private void registerOneOffEvent(double trigger, Command func) {
        TimedEvent newEvent = new TimedEvent(trigger, trigger, func);
        oneOffEvents.add(newEvent);
    }

}