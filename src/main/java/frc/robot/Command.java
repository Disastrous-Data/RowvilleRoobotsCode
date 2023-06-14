package frc.robot;

public interface Command {
    public AutoTaskResult Execute(TankDrive drive, HardwareStates states);
}
