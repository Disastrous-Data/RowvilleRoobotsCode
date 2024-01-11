/*
TimedEvent.java
Written by CoPokBl

This is a schema for a timed event that can be run in auto.
It is just used to store the information from it function call.

Don't change this.
*/

package com.disastrousdata;

public class TimedEvent {
    public double StartTime;
    public double EndTime;
    public Command Method;

    public TimedEvent(double start, double end, Command func) {
        StartTime = start;
        EndTime = end;
        Method = func;
    }
    
}
