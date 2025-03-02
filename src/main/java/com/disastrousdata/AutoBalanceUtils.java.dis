/*
AutoBalanceUtils.java
Written by CoPokBl

This entire file just contains the code for the auto balance system.
Running balance with the current robot hardware information will return
the motor states needed to balance. It should be called every tick and
the states returned should be applied to the robot.

If the robot just spins when this is run it means there is an error.
I don't what the error is, but it is definitely an error.
*/

package com.disastrousdata;

public class AutoBalanceUtils {

    private static final double OFF_BALANCE_ANGLE_THRESHOLD_DEGREES = 0;
    private static final double OON_BALANCE_ANGLE_THRESHOLD_DEGREES = 0;

    private boolean autoBalanceXMode;
    private boolean autoBalanceYMode;

    public AutoBalanceUtils() {

    }

    public HardwareStates Balance(TankDrive drive, HardwareStates states) {
        double xAxisRate = 0;
        double yAxisRate = 0;

        double pitchAngleDegrees = drive.Hardware.NavX.getPitch();
        double rollAngleDegrees = drive.Hardware.NavX.getRoll();

        Dash.set("roll", rollAngleDegrees);
        Dash.set("pitch", pitchAngleDegrees);

        if (!autoBalanceXMode && (Math.abs(pitchAngleDegrees) >= Math.abs(OFF_BALANCE_ANGLE_THRESHOLD_DEGREES))) {
            autoBalanceXMode = true;
        } else if (autoBalanceXMode && (Math.abs(pitchAngleDegrees) <= Math.abs(OON_BALANCE_ANGLE_THRESHOLD_DEGREES))) {
            autoBalanceXMode = false;
        }
        if (!autoBalanceYMode && (Math.abs(pitchAngleDegrees) >= Math.abs(OFF_BALANCE_ANGLE_THRESHOLD_DEGREES))) {
            autoBalanceYMode = true;
        } else if (autoBalanceYMode && (Math.abs(pitchAngleDegrees) <= Math.abs(OON_BALANCE_ANGLE_THRESHOLD_DEGREES))) {
            autoBalanceYMode = false;
        }

        // Control drive system automatically,
        // driving in reverse direction of pitch/roll angle,
        // with a magnitude based upon the angle
        Dash.set("x Balance Enabled", autoBalanceXMode);
        Dash.set("y Balance Enabled", autoBalanceYMode);
        if (autoBalanceXMode) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            xAxisRate = Math.sin(pitchAngleRadians) * -1;
        }
        if (autoBalanceYMode) {
            double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
            yAxisRate = Math.sin(rollAngleRadians) * -1;
        }

        try {
            double leftAxisRate = yAxisRate + xAxisRate;
            double rightAxisRate = yAxisRate - xAxisRate;
            Dash.set("yAxisRate", yAxisRate);
            Dash.set("xAxisRate", xAxisRate);
            Dash.set("rightAxisRate", rightAxisRate);
            Dash.set("leftAxisRate", leftAxisRate);
            states.LeftDriveMotors = (leftAxisRate*-1);
            states.RightDriveMotors = (leftAxisRate*-1);
        } catch (RuntimeException ex) {
            // This should never happen
            // So if it does the robot can spin
            states.LeftDriveMotors = -0.3;
            states.RightDriveMotors = 0.3;
        }
        return states;
    }

}
