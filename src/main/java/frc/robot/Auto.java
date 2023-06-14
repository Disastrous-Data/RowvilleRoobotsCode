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

    private List<Command> tasks = new ArrayList<>();

    private boolean autoBalanceXMode;
    private boolean autoBalanceYMode;

    private ArrayList<Double> leftSpeeds = new ArrayList<>();
    private ArrayList<Double> Pitches = new ArrayList<>();

    // Add timed events using registerTimedEvent only in Init().
    public void Init() {

        registerTask(new Command() {
            public AutoTaskResult Execute(
                TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.6;
                states.RightDriveMotors = 0.6;
                return new AutoTaskResult(states, false);
            }
        });

        registerTask(new Command() {
            public AutoTaskResult Execute(
                TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.35;
                states.RightDriveMotors = 0.35;
                return new AutoTaskResult(states, false);
            }
        });

        registerTask(new Command() {
            public AutoTaskResult Execute(TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = -0.3;
                states.RightDriveMotors = -0.3;
                return new AutoTaskResult(states, false);
            }
        });

        // Balance
        registerTask(new Command() {
            public AutoTaskResult Execute(TankDrive drive, HardwareStates states) {
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
                }
                if (autoBalanceYMode) {
                    SmartDashboard.putBoolean("y Balance Enabled", autoBalanceYMode);
                    double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
                    yAxisRate = Math.sin(rollAngleRadians) * -1;
                }

                try {
                    //myRobot.driveCartesian(xAxisRate, yAxisRate, stick.getTwist(), 0);
                    double leftAxisRate = yAxisRate + xAxisRate;
                    double rightAxisRate = yAxisRate - xAxisRate;
                    SmartDashboard.putNumber("yAxisRate", yAxisRate);
                    SmartDashboard.putNumber("xAxisRate", xAxisRate);
                    SmartDashboard.putNumber("rightAxisRate", rightAxisRate);
                    SmartDashboard.putNumber("leftAxisRate", leftAxisRate);
                    SmartDashboard.putBoolean("isBalanced", leftAxisRate == 0 && rightAxisRate == 0);
                    leftSpeeds.add(leftAxisRate);
                    SmartDashboard.putNumberArray("leftAxisRate", leftSpeeds.stream().mapToDouble(o -> ((Number) o).doubleValue()).toArray());
                    states.LeftDriveMotors = (leftAxisRate*0.85);
                    states.RightDriveMotors = (leftAxisRate*0.85);
                } catch (RuntimeException ex) {
                    // IDK MAN
                }
                return new AutoTaskResult(states, false);
            }
        });

    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead.

    static final double kOffBalanceAngleThresholdDegrees = 0;
    static final double kOonBalanceAngleThresholdDegrees = 0;

    public void Invoke(AutoState state) {
        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);

        Command event = tasks.get(0);
        AutoTaskResult states = event.Execute(state.drive, new HardwareStates());

        // ARM LIMIT SWITCH
        if (state.drive.Hardware.ArmLimitSwitch.get() && states.States.ArmUpDownMotors < 0) {
            states.States.ArmUpDownMotors = 0;
        }

        state.drive.Update(states.States);

        if (states.IsFinished) {
            tasks.remove(0);
        }
    }

    private void registerTask(Command func) {
        tasks.add(func);
    }

}