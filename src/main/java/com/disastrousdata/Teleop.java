package com.disastrousdata;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teleop {

    // Constants
    private static final double axis0Offset = -0.02;
    private static final double axis1Offset = 0;
    private static final double stallThreshHold = 0.5;

    private static final int[] buttonIds = new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9
    };

    private double timeSinceLastTickTimestamp = 0;

    private final HashMap<Integer, Boolean> lastButtonStates = new HashMap<>();
    private final List<Integer> justPressed = new ArrayList<>();


    @SuppressWarnings("unused")  // WILL BE USED IN FUTURE
    private boolean wasJustPressed(int id) {
        return justPressed.contains(id);
    }

    public void Invoke(TankDrive drive, double time) {
        HardwareStates states = new HardwareStates();   

        // This tracks the time since the last tick
        // delta is the time since the last tick in seconds
        @SuppressWarnings("unused") double delta = time - timeSinceLastTickTimestamp;  // WILL BE USED IN FUTURE
        timeSinceLastTickTimestamp = time;

        // Button tracking
        // You can have code run when a button is pressed by using if wasJustPressed(buttonId)
        justPressed.clear();
        for (int id : buttonIds) {
            boolean buttonState = drive.Controller.getRawButton(id);
            boolean lastButtonState = lastButtonStates.getOrDefault(id, false);
            if (buttonState && !lastButtonState) {
                justPressed.add(id);
            }
            lastButtonStates.put(id, buttonState);
        }

        // |---------------------------------------------------|
        // | ALL CODE GOES AFTER THIS SO THAT STOP LOGIC WORKS |
        // |---------------------------------------------------|

        double fb = drive.Controller.getY() + axis1Offset / 2;
        double lr = drive.Controller.getX() + axis0Offset / 2;
        lr = lr * 0.5;
        double leftDriveValue = fb - lr;
        double rightDriveValue = fb + lr;

        states.LeftDriveMotors = leftDriveValue;
        states.RightDriveMotors = rightDriveValue;

        drive.Update(states);
        SmartDashboard.putNumber("outputCurrent", drive.Hardware.LeftMotor2.getOutputCurrent());
        SmartDashboard.putBoolean("isStalled", drive.Hardware.LeftMotor2.getOutputCurrent() > stallThreshHold);
        SmartDashboard.putBoolean("isLimitSwitch", drive.Hardware.LimitSwitch.get());
    }

}