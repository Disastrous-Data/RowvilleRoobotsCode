package frc.robot;

public class AutoTaskResult {
    public HardwareStates States;
    public boolean IsFinished;

    public AutoTaskResult(HardwareStates states, boolean isFinished) {
        States = states;
        IsFinished = isFinished;
    }
}