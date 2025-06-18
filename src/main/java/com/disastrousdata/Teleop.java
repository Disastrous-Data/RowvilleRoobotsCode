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

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teleop {

    // Constants

    /** Sometimes the joystick un-calibrates itself so use this to offset the raw input */
    private static final double axis0Offset = 0;

    /** Sometimes the joystick un-calibrates itself so use this to offset the raw input */
    private static final double axis1Offset = 0;

    /** This doesn't do anything except change when the 'isStalled' value on the Dash is changed */
    private static final double stallThreshHold = 0.5;

    /** A list of buttons to check for input, this should work for most controllers */
    private static final int[] buttonIds = new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    };

    /** Used to calculate delta time */
    private double timeSinceLastTickTimestamp = 0;

    /** Used to calculate wasJustPressed states for buttons, only works for buttons in buttonIds */
    private final HashMap<Integer, Boolean> lastButtonStates = new HashMap<>();
    private final List<Integer> justPressed = new ArrayList<>();
    private final List<Integer> pressedButtons = new ArrayList<>();

    /**
     * Gets whether the specified bind was pressed on that 'frame'.
     * <p>
     * If you are checking if wasJustPressed(someBind) and it is pressed
     * the condition will be true exactly once, until the button
     * is repressed.
     */
    private boolean wasJustPressed(Keybind bind) {
        return justPressed.contains(bind.buttonId);
    }

    /** Gets whether the button is being currently pressed */
    private boolean isPressed(Keybind bind) {
        return pressedButtons.contains(bind.buttonId);
    }

    /// <summary>
    /// Controls a motor based on the input of two buttons.
    /// </summary>
    private void motorControls(Keybind pos, Keybind neg, MotorController motor, double speed) {
        if (isPressed(pos)) {
            motor.set(speed);
        } else if (isPressed(neg)) {
            motor.set(-speed);
        } else {
            motor.set(0);
        }
    }

    /**
     * Invoked once every 'tick' of the robot code, equivalent of teleopPeriodic().
     * <p>
     * `time` is the elapsed seconds since teleop began.
     * `drive` is the TankDrive object used to control the robot.
     */
    public void invoke(TankDrive drive, double time) {
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
            boolean buttonState = drive.controller.getRawButton(id);
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
        // | ALL CODE GOES AFTER THIS SO THAT BUTTONS WORK     |
        // |---------------------------------------------------|

        // Intake, the thing that ejects the pipe
        final double INTAKE_SPEED = Dash.get("ejectSpeed");
        motorControls(Keybind.INTAKE_IN, Keybind.INTAKE_OUT, drive.intake, INTAKE_SPEED);
        states.setIntake(drive.intake.get());

        // states.setIntake(drive.hardware.controller.getRawAxis(3));

        // Drive
        double fb = drive.controller.getY() + axis1Offset / 2;
        double lr = drive.controller.getX() + axis0Offset / 2;
        lr = lr * -0.5;
        double leftDriveValue = fb - lr;
        double rightDriveValue = fb + lr;

        Dash.set("leftMotor", leftDriveValue);
        Dash.set("rightMotor", rightDriveValue);

        states.setLeftDriveMotors(leftDriveValue);
        states.setRightDriveMotors(rightDriveValue);

        // Front Flap
        final double ARM_SPEED = 0.8;
        if (isPressed(Keybind.ARM_UP)){
            drive.setArmPower(ARM_SPEED);
        }else if (isPressed(Keybind.ARM_DOWN)){
            drive.setArmPower(-ARM_SPEED);
        }

        // Apply values and dump debug info
        drive.update(states);  // PUT MOVEMENT CODE BEFORE THIS LINE
        Dash.set("outputCurrent", drive.hardware.leftMotor2.getOutputCurrent());
        Dash.set("isStalled", drive.hardware.leftMotor2.getOutputCurrent() > stallThreshHold);
    }

}