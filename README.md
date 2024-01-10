# Disastrous Data Robot Code
This is the codebase for our 2024 robot for FRC.
It is written in Java and uses the WPILib library.

## Contributing
To edit the auto routine, edit the Auto.java file.
Find the init function and register either timedevents or oneoffevents.

To edit the teleop control, edit the Teleop.java file.
In the Invoke() function you can add code to control the robot.
To control motors and similar hardware you should modify the states variable.
You shouldn't directly access hardware with the drive variable unless
it does not exist in the states variable. You can get the delta time with 
the delta variable, and you can have code run once when a button is pressed
by adding `if (wasJustPressed(buttonId))` to the code.