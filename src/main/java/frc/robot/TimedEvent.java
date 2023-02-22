package frc.robot;

public class TimedEvent {
    public int StartTime;
    public int EndTime;
    public Command Method;

    public TimedEvent(int start, int end, Command func) {
        StartTime = start;
        EndTime = end;
        Method = func;
    }
    
}
