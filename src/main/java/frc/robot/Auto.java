package frc.robot;

// In auto we want to do the following
// - Extend arm
// - Place cone on a base
// - Move arm away
// - Go back out of the line

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

    private List<TimedEvent> events = new ArrayList<>();
    private boolean hasUnclawed = false;

    // Add timed events using registerTimedEvent only in Init().
    public void Init() {
        // Register timed events
        registerTimedEvent(0, 0.5, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.ArmInOutMotors = 0.5;
                states.ClawPiston = true;
                return states;
            }
        });
        registerTimedEvent(0.5, 1, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.ClawPiston = false;
                hasUnclawed = true;
                return states;
            }
        });
        registerTimedEvent(3, 5, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.2;
                states.RightDriveMotors = 0.2;
                return states;
            }
        });
    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead.
    public void Invoke(AutoState state) {
        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);
        for (TimedEvent event : events) {
            if (state.timeElapsed >= event.StartTime && state.timeElapsed <= event.EndTime) {
                HardwareStates states = event.Method.Execute(state.drive, new HardwareStates());
                
                // ARM LIMIT SWITCH
                if (state.drive.Hardware.ArmLimitSwitch.get() && states.ArmUpDownMotors < 0) {
                    states.ArmUpDownMotors = 0;
                }

                state.drive.Update(states);
            }
        }
    }

    private void registerTimedEvent(double start, double end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

}