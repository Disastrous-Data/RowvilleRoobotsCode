/*
Command.java
Written by CoPokBl

This is a schema for a command that can be run in auto.
This exists so that you can override the Execute method in a lambda.
*/

package com.disastrousdata;

public interface Command {
    HardwareStates Execute(TankDrive drive, HardwareStates states);
}
