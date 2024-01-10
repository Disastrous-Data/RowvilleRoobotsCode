package com.disastrousdata;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBalanceUtils {

    private static final double kOffBalanceAngleThresholdDegrees = 0;
    private static final double kOonBalanceAngleThresholdDegrees = 0;

    private boolean autoBalanceXMode;
    private boolean autoBalanceYMode;

    public AutoBalanceUtils() {

    }

    public HardwareStates Balance(TankDrive drive, HardwareStates states) {
        double xAxisRate = 0;
        double yAxisRate = 0;

        double pitchAngleDegrees = drive.Hardware.NavX.getPitch();
        double rollAngleDegrees = drive.Hardware.NavX.getRoll();

        SmartDashboard.putNumber("roll", rollAngleDegrees);
        SmartDashboard.putNumber("pitch", pitchAngleDegrees);

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
        SmartDashboard.putBoolean("x Balance Enabled", autoBalanceXMode);
        SmartDashboard.putBoolean("y Balance Enabled", autoBalanceYMode);
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
            SmartDashboard.putNumber("yAxisRate", yAxisRate);
            SmartDashboard.putNumber("xAxisRate", xAxisRate);
            SmartDashboard.putNumber("rightAxisRate", rightAxisRate);
            SmartDashboard.putNumber("leftAxisRate", leftAxisRate);
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
