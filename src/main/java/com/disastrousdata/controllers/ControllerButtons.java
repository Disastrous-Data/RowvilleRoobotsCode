/*
ControllerButtons.java
Written by CoPokBl

This contains the IDs of all the buttons on the controller.
Controller: Logitech Gamepad F310
*/

package com.disastrousdata.controllers;

public enum ControllerButtons implements Controller {
    A(0),
    B(1),
    X(2),
    Y(3),
    LEFT_BUMPER(4),
    RIGHT_BUMPER(5),
    BACK(6),
    START(7),
    LEFT_STICK(8),
    RIGHT_STICK(9);

    public final int buttonId;

    ControllerButtons(int id) {
        this.buttonId = id;
    }

    @Override
    public int getButtonId() {
        return buttonId;
    }
}
