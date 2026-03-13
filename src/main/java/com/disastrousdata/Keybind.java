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
    // Front intake and shooter
    INTAKE_IN(Joystick.SIDE_BUTTON),
    INTAKE_OUT(Joystick.TRIGGER),

    // Intake fuel from "fuel tank" (polycarb box)
    ARM_UP(Joystick.BASE_7),
    ARM_DOWN(Joystick.BASE_9);

    public final int buttonId;

    Keybind(Controller button) {
        this.buttonId = button.getButtonId();
    }
}
