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
        Mobility,

        /**
         * Do absolutely nothing.
         * Just sit there and cry.
         */
        Nothing,

        /**
         * Mobility but slower.
         */
        SlowMobility,

        /**
         * Drive forward and score the pre loaded coral.
         */
        ScoreCoral,

        StallTest
    }

    /** Initialise autonomous with a given routine from the AutoMode list */
    public void init(AutoMode mode) {  // Add timed events using registerTimedEvent only in Init().
        // Register timed events
        switch (mode) {
            case Mobility:
                // Go onto the start of the charge station
                // Anonymous Lambda - unnamed function
                registerTimedEvent(0,3, (drive, states) -> {
                    states.setLeftDriveMotors(-0.6);
                    states.setRightDriveMotors(-0.6 + Dash.get("leftBias"));
                    Dash.set("effectiveRightMotor", states.getRightDriveMotors());
                    return true;
                });
                break;
            
            case SlowMobility:
                // Go onto the start of the charge station
                // Anonymous Lambda - unnamed function
                registerTimedEvent(0,6, (drive, states) -> {
                    states.setLeftDriveMotors(-0.3);
                    states.setRightDriveMotors(-0.3);
                    return true;
                });
                break;

            case ScoreCoral:
                // Left
                // registerTimedEvent(0, 100, (drive, states) -> {
                //     states.setLeftDriveMotors(0.3);
                //     if (drive.hardware.leftMotor2.getOutputCurrent() > 20) {
                //         return false;
                //     }
                //     return true;
                // });
                // // Right
                // registerTimedEvent(0, 100, (drive, states) -> {
                //     states.setRightDriveMotors(0.3);
                //     if (drive.hardware.rightMotor2.getOutputCurrent() > 20) {
                //         return false;
                //     }
                //     return true;
                // });

                registerTimedEvent(0, 6, (drive, states) -> {
                    states.setDriveMotors(-0.2);
                    return true;
                });

                registerTimedEvent(7, 10, (drive, states) -> {
                    states.setIntake(Dash.get("ejectSpeed"));
                    return true;
                });

                registerTimedEvent(10, 12, (drive, states) -> {
                    states.setIntake(Dash.get("ejectSpeed") + 0.1);
                    return true;
                });

                registerTimedEvent(12, 14, (drive, states) -> {
                    states.setIntake(Dash.get("ejectSpeed") + 0.2);
                    return true;
                });

                registerTimedEvent(14, 15, (drive, states) -> {
                    states.setIntake(1);
                    return true;
                });
                break;

            case StallTest:
                // Left
                registerTimedEvent(0, 100, (drive, states) -> {
                    states.setLeftDriveMotors(0.3);
                    if (drive.hardware.leftMotor2.getOutputCurrent() > 0.5) {
                        return false;
                    }
                    return true;
                });
                // Right
                registerTimedEvent(0, 100, (drive, states) -> {
                    states.setRightDriveMotors(0.3);
                    if (drive.hardware.rightMotor2.getOutputCurrent() > 0.5) {
                        return false;
                    }
                    return true;
                });
                break;

            case Nothing:
            default:
                break;
        }
    }
    
    // Invoked periodically during Auto, don't modify this method. Use registerTimedEvent in the init()
    // function instead to set the auto routine.
    public void invoke(AutoState state) {
        Dash.set("AutoTimer", state.timeElapsed);
        HardwareStates states = new HardwareStates();

        for (TimedEvent event : events) {
            if (!event.complete && state.timeElapsed >= event.startTime && state.timeElapsed <= event.endTime) {
                if (!event.method.execute(state.drive, states)) {
                    event.markComplete();
                }
            }
        }
        synchronized (oneOffEvents) {
            for (TimedEvent oneOffEvent : oneOffEvents) {
                if (oneOffEvent.complete) {
                    continue;  // Don't run completed events
                }
                if (oneOffEvent.startTime <= state.timeElapsed) {
                    HardwareStates hardwareStates = new HardwareStates();
                    oneOffEvent.method.execute(state.drive, hardwareStates);
                    state.drive.update(hardwareStates);
                    oneOffEvent.markComplete();
                }
            }
        }
        
        state.drive.update(states);
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
        synchronized (oneOffEvents) {
            oneOffEvents.add(newEvent);
        }
    }

}