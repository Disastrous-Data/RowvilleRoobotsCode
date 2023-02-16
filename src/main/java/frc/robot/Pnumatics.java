package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pnumatics {

    public void SetState(boolean state) {
        DoubleSolenoid sol = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
        if (state) {
            // Enable
            sol.set(Value.kForward);
        } else {
            // Disable
            sol.set(Value.kReverse);
        }
        sol.close();
    }

}
