package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

    private List<TimedEvent> events = new ArrayList<>();

    // Add timed events using registerTimedEvent only in Init().
    public void Init() {
        // Register timed events
        registerTimedEvent(0, 3, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.5;
                states.RightDriveMotors = -0.5;
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
                state.drive.Update(states);
            }
        }
    }

    private void registerTimedEvent(int start, int end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

}