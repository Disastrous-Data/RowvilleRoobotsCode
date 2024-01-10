package com.disastrousdata;

// In auto we want to do the following
// - Extend arm
// - Place cone on a base
// - Move arm away
// - Go back out of the line

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

    private final List<TimedEvent> events = new ArrayList<>();
    private final List<TimedEvent> oneOffEvents = new ArrayList<>();


    // Add timed events using registerTimedEvent only in Init().
    public void Init() {
        // Register timed events

        // START EXIT HOME

        // Back out of home area
        // registerTimedEvent(0, 2, new Command() {
        //     public HardwareStates Execute(
        //         TankDrive drive, HardwareStates states) {
        //         states.LeftDriveMotors = 0.6;
        //         states.RightDriveMotors = 0.6;
        //         return states;
        //     }
        // });

        // START DOCk

    
        // Go onto the start of the charge station
        registerTimedEvent(0,1, (drive, states) -> {
            states.LeftDriveMotors = 0.6;
            states.RightDriveMotors = 0.6;
            return states;
        });

        // Go over the charge station and out of home zone
        registerTimedEvent(1,4, (drive, states) -> {
            states.LeftDriveMotors = 0.35;
            states.RightDriveMotors = 0.35;
            return states;
        });

        // Back back onto the charge station
        registerTimedEvent(4, 6.6, (drive, states) -> {
            states.LeftDriveMotors = -0.4;
            states.RightDriveMotors = -0.4;
            return states;
        });

        // Balance
        AutoBalanceUtils balancer = new AutoBalanceUtils();
        registerTimedEvent(6.6, 25, balancer::Balance);

    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead to set the auto routine.
    public void Invoke(AutoState state) {
        SmartDashboard.putNumber("AutoTimer", state.timeElapsed);
        boolean didDoSomething = false;
        for (TimedEvent event : events) {
            if (state.timeElapsed >= event.StartTime && state.timeElapsed <= event.EndTime) {
                HardwareStates states = event.Method.Execute(state.drive, new HardwareStates());
                state.drive.Update(states);
                didDoSomething = true;
            }
        }
        for (TimedEvent oneOffEvent : oneOffEvents) {
            if (oneOffEvent.StartTime <= state.timeElapsed) {
                HardwareStates states = oneOffEvent.Method.Execute(state.drive, new HardwareStates());
                state.drive.Update(states);
                oneOffEvents.remove(oneOffEvent);
                didDoSomething = true;
            }
        }
        if (!didDoSomething) {
            state.drive.Update(new HardwareStates());
        }
    }

    // Register an event that is fired each tick between start and end time.
    private void registerTimedEvent(double start, double end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

    // Register an event that is fired once at the specified time.
    @SuppressWarnings("unused")  // May be used in the future
    private void registerOneOffEvent(double trigger, Command func) {
        TimedEvent newEvent = new TimedEvent(trigger, trigger, func);
        oneOffEvents.add(newEvent);
    }

}