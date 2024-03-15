/*
JoystickButtons.java
Written by CoPokBl

This contains the IDs of all the buttons on the joystick.
Controller: Logitech Extreme 3D Pro
*/

package com.disastrousdata.controllers;

public enum JoystickButtons implements Controller {
    TRIGGER(1),
    SIDE_BUTTON(2),
    BOTTOM_LEFT_3(3),
    BOTTOM_RIGHT_4(4),
    TOP_LEFT_5(5),
    TOP_RIGHT_6(6),
    BASE_7(7),
    BASE_8(8),
    BASE_9(9),
    BASE_10(10),
    BASE_11(11),
    BASE_12(12);

    public final int buttonId;

    JoystickButtons(int id) {
        this.buttonId = id;
    }

    @Override
    public int getButtonId() {
        return buttonId;
    }
}
