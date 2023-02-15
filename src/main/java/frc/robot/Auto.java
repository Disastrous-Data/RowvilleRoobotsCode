package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

    private List<TimedEvent> events = new ArrayList<>();

    public void Init() {
        // Register timed events
        registerTimedEvent(0, 0, new Command() {
            public void Execute(Hardware hardware, TankDrive drive) {
                
            }
        });
    }
    
    public void Invoke(AutoState state) {
        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);
    }

    private void registerTimedEvent(int start, int end, Command func) {
        TimedEvent newEvent = new TimedEvent();
        events.add(newEvent);
    }

}