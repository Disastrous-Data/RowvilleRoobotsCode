/*
TimedEvent.java
Written by CoPokBl

This is a schema for a timed event that can be run in auto.
It is just used to store the information from it function call.

Don't change this.
*/

package com.disastrousdata;

public class TimedEvent {
    public double startTime;
    public double endTime;
    public Command method;
    public boolean complete;

    public TimedEvent(double start, double end, Command func) {
        startTime = start;
        endTime = end;
        method = func;
    }

    public void markComplete() {
        complete = true;
    }
}
