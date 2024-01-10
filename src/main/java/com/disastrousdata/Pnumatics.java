package com.disastrousdata;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pnumatics {

    DoubleSolenoid sol;

    public Pnumatics(DoubleSolenoid solenoid) {
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
