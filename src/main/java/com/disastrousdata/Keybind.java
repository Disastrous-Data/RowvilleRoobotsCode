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
//import com.disastrousdata.controllers.XBoxController;

//import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public enum Keybind {
    INTAKE_IN(Joystick.TRIGGER),
    INTAKE_OUT(Joystick.SIDE_BUTTON),

    // Front flap
    ARM_UP(Joystick.TOP_LEFT_5),
    ARM_DOWN(Joystick.BOTTOM_LEFT_3);

    public final int buttonId;

    Keybind(Controller button) {
        this.buttonId = button.getButtonId();
    }
}
