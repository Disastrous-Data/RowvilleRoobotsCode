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
    private List<TimedEvent> oneOffEvents = new ArrayList<>();

    // Add timed events using registerTimedEvent only in Init().
    public void Init() {
        // Register timed events
        // registerOneOffEvent(0, new Command() {
        //     public HardwareStates Execute(TankDrive drive, HardwareStates states) {
        //         drive.Leds.LEDRainbow();
        //         return states;
        //     }
        // });
        registerTimedEvent(0, 2.55, new Command() {
            public HardwareStates Execute(TankDrive drive, HardwareStates states) {
                states.LeftDriveMotors = 0.4;
                states.RightDriveMotors = 0.4;
                return states;
            }
        });

    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead.
    public void Invoke(AutoState state) {
        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);
        boolean didDoSomething = false;
        for (TimedEvent event : events) {
            if (state.timeElapsed >= event.StartTime && state.timeElapsed <= event.EndTime) {
                HardwareStates states = event.Method.Execute(state.drive, new HardwareStates());
                
                // ARM LIMIT SWITCH
                if (state.drive.Hardware.ArmLimitSwitch.get() && states.ArmUpDownMotors < 0) {
                    states.ArmUpDownMotors = 0;
                }

                state.drive.Update(states);
                didDoSomething = true;
            }
        }
        for (TimedEvent oneOffEvent : oneOffEvents) {
            if (oneOffEvent.StartTime <= state.timeElapsed) {
                HardwareStates states = oneOffEvent.Method.Execute(state.drive, new HardwareStates());
                
                // ARM LIMIT SWITCH
                if (state.drive.Hardware.ArmLimitSwitch.get() && states.ArmUpDownMotors < 0) {
                    states.ArmUpDownMotors = 0;
                }

                state.drive.Update(states);
                oneOffEvents.remove(oneOffEvent);
                didDoSomething = true;
            }
        }
        if (!didDoSomething) {
            state.drive.Update(new HardwareStates());
        }
    }

    private void registerTimedEvent(double start, double end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

    private void registerOneOffEvent(double trigger, Command func) {
        TimedEvent newEvent = new TimedEvent(trigger, trigger, func);
        oneOffEvents.add(newEvent);
    }

}