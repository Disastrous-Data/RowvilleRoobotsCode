/*
Teleop.java
Written by CoPokBl

This contains all teleop code and a nice framework for executing it.
Invoke is called every tick in teleopPeriodic in Robot.java.

Code to actually control the robot should be added after the big comment to ensure
all stop logic and framework code works properly.

To check if a button is currently pressed use drive.Controller.getRawButton(buttonId)
To check if a button was just pressed use wasJustPressed(keybind)
If you use wasJustPressed(keybind) it will return true only once when the button is pressed.

Put every button you plan on using into buttonIds so that it will be tracked.

You can also add variables to the state variables section to keep track of things.
*/

package com.disastrousdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teleop {

    // Constants
    private static final double axis0Offset = -0.02;
    private static final double axis1Offset = 0;
    private static final double stallThreshHold = 0.5;

    private static final int[] buttonIds = new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    };

    private double timeSinceLastTickTimestamp = 0;

    private final HashMap<Integer, Boolean> lastButtonStates = new HashMap<>();
    private final List<Integer> justPressed = new ArrayList<>();
    private final List<Integer> pressedButtons = new ArrayList<>();

    // STATE VARIABLES
    private boolean isIntakeOn = false;
    private boolean hasGamePiece = false;

    private boolean wasJustPressed(Keybinds bind) {
        return justPressed.contains(bind.buttonId);
    }

    private boolean isPressed(Keybinds bind) {
        return pressedButtons.contains(bind.buttonId);
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
        pressedButtons.clear();
        for (int id : buttonIds) {
            boolean buttonState = drive.Controller.getRawButton(id);
            boolean lastButtonState = lastButtonStates.getOrDefault(id, false);
            if (buttonState && !lastButtonState) {
                justPressed.add(id);
            }
            lastButtonStates.put(id, buttonState);

            if (buttonState) {
                pressedButtons.add(id);
            }
        }

        // |---------------------------------------------------|
        // | ALL CODE GOES AFTER THIS SO THAT STOP LOGIC WORKS |
        // |---------------------------------------------------|

        if (wasJustPressed(Keybinds.INTAKE_TOGGLE)) {
            if (hasGamePiece) {  // Shoot it
                hasGamePiece = false;
                drive.SetIntakeMode(TankDrive.IntakeMode.SHOOT);
            } else {  // Toggle intake mode
                isIntakeOn = !isIntakeOn;
                drive.SetIntakeMode(isIntakeOn ? TankDrive.IntakeMode.INTAKE : TankDrive.IntakeMode.OFF);
            }
        }

        if (wasJustPressed(Keybinds.INTAKE_READY)) {  // TODO: Check Game piece detected with line break/limit switch
            hasGamePiece = true;
            Dash.set("hasGamePiece", true);
            drive.SetIntakeMode(TankDrive.IntakeMode.CHARGE);
        } else {
            Dash.set("hasGamePiece", false);
        }

        final double ARM_SPEED = 0.8;
        if (isPressed(Keybinds.ARM_UP)) {
            drive.setArmPower(ARM_SPEED);
        } else if (isPressed(Keybinds.ARM_DOWN)) {
            drive.setArmPower(-ARM_SPEED);
        } else {
            drive.setArmPower(0);
        }

        final double CLAW_SPEED = 0.5;
        if (isPressed(Keybinds.CLAW_UP)) {
            drive.setClawPower(CLAW_SPEED);
        } else if (isPressed(Keybinds.CLAW_DOWN)) {
            drive.setClawPower(-CLAW_SPEED);
        } else {
            drive.setClawPower(0);
        }

        double fb = drive.Controller.getY() + axis1Offset / 2;
        double lr = drive.Controller.getX() + axis0Offset / 2;
        lr = lr * 0.5;
        double leftDriveValue = fb - lr;
        double rightDriveValue = fb + lr;

        states.LeftDriveMotors = leftDriveValue;
        states.RightDriveMotors = rightDriveValue;

        drive.Update(states);  // PUT MOVEMENT CODE BEFORE THIS LINE
        Dash.set("outputCurrent", drive.Hardware.LeftMotor2.getOutputCurrent());
        Dash.set("isStalled", drive.Hardware.LeftMotor2.getOutputCurrent() > stallThreshHold);
        //Dash.set("isLimitSwitch", drive.Hardware.LimitSwitch.get());
    }

}