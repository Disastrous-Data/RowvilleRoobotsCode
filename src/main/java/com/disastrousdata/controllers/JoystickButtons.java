/*
JoystickButtons.java
Written by CoPokBl

This contains the IDs of all the buttons on the joystick.
Controller: Logitech Extreme 3D Pro
*/

package com.disastrousdata.controllers;

public enum JoystickButtons implements Controller {
    TRIGGER(1),
    SIDE_BUTTON(1),
    BOTTOM_LEFT_3(2),
    BOTTOM_RIGHT_4(3),
    TOP_LEFT_5(4),
    TOP_RIGHT_6(5),
    BASE_7(6),
    BASE_8(7),
    BASE_9(8),
    BASE_10(9),
    BASE_11(10),
    BASE_12(11);

    public final int buttonId;

    JoystickButtons(int id) {
        this.buttonId = id;
    }
}
