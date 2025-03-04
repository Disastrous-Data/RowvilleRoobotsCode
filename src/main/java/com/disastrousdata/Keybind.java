/*
ControllerButtons.java
Written by CoPokBl

This enum maps controller buttons to actions.
Use Joystick and XBoxController to source button IDs.

Add any new actions to this class with their respective binding. 
*/

package com.disastrousdata;

import com.disastrousdata.controllers.Controller;
import com.disastrousdata.controllers.Joystick;

public enum Keybind {
    INTAKE_IN(Joystick.BOTTOM_RIGHT_4),
    INTAKE_OUT(Joystick.TOP_RIGHT_6);

    public final int buttonId;

    Keybind(Controller button) {
        this.buttonId = button.getButtonId();
    }
}
