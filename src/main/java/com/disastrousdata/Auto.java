/*
Auto.java
Written by CoPokBl

This contains the framework and auto routine code.
To modify the auto routine, modify the Init() function and add/modify the registerTimedEvent lines.
In the invocation, pass the time in seconds since the start of auto to start executing it and the time
in seconds since the start of auto to stop executing it. new Command() will be called each tick
in between those times.

The command Execute() function should take the states variable it is given and modify it to change
the speeds of the motors. It should then return the states variable it was given. The drive variable
can be used to control some things that aren't available in the states variable.
AVOID USING THE DRIVE VARIABLE, ONLY USE IT IF THERE IS NO OTHER WAY TO DO SOMETHING.

If you need to run a command once at a specific time, use registerOneOffEvent instead of registerTimedEvent,
and pass the time in seconds since the start of auto to run the command at.
*/

package com.disastrousdata;

import java.util.ArrayList;
import java.util.List;

public class Auto {
    /** The loaded Timed Events, events that run from a seconds to b seconds */
    private final List<TimedEvent> events = new ArrayList<>();

    /** The loaded one-off events, an event that runs once */
    private final List<TimedEvent> oneOffEvents = new ArrayList<>();

    /** A list of configured auto routines */
    public enum AutoMode {
        /**
         * We only want the mobility points nothing else
         * the path in front of us must be clear otherwise we
         * will hit something.
         * Face away from wall but against it.
         */
        Nothing,

        /**
         * Do absolutely nothing.
         * Just sit there and cry.
         */
        Mobility
    }

    /** Initialise autonomous with a given routine from the AutoMode list */
    public void Init(AutoMode mode) {  // Add timed events using registerTimedEvent only in Init().
        // Register timed events
        switch (mode) {
            case Mobility:
                // Go onto the start of the charge station
                registerTimedEvent(0,3, (drive, states) -> {
                    states.LeftDriveMotors = 0.6;
                    states.RightDriveMotors = 0.6;
                    return states;
                });
                break;

            case Nothing:
            default:
                break;
        }
    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead to set the auto routine.
    public void Invoke(AutoState state) {
        Dash.set("AutoTimer", state.timeElapsed);
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

    /**
     * Register an event that is fired each tick between start and end time.
     * Multiple can run at the same time
     */
    private void registerTimedEvent(double start, double end, Command func) {
        TimedEvent newEvent = new TimedEvent(start, end, func);
        events.add(newEvent);
    }

    /**
     * Register an event that is fired once at the specified time.
     * Can happen during a timed event.
     */
    @SuppressWarnings("unused")  // May be used in the future
    private void registerOneOffEvent(double trigger, Command func) {
        TimedEvent newEvent = new TimedEvent(trigger, trigger, func);
        oneOffEvents.add(newEvent);
    }

}