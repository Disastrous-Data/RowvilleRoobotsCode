package frc.robot;

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
