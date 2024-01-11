/*
Pneumatics.java
Written by CoPokBl

That is a very hard word to spell.
This is a wrapper class for controlling pneumatics.
Instantiate it with a DoubleSolenoid and then use SetState to set the state of the solenoid.
It isn't that necessary, but it makes code easier to read.
*/

package com.disastrousdata;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pneumatics {

    DoubleSolenoid sol;

    public Pneumatics(DoubleSolenoid solenoid) {
        sol = solenoid;
    }

    @SuppressWarnings("unused")  // I KNOW IT'S NOT USED, IT WILL IN THE FUTURE
    public void SetState(boolean state) {
        if (state) {
            // Enable
            sol.set(Value.kForward);
        } else {
            // Disable
            sol.set(Value.kReverse);
        }
    }

}
