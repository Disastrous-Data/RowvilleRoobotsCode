package frc.robot;

public interface Command {
    public HardwareStates Execute(TankDrive drive, HardwareStates states);
}
