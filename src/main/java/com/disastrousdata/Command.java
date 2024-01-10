package com.disastrousdata;

public interface Command {
    HardwareStates Execute(TankDrive drive, HardwareStates states);
}
