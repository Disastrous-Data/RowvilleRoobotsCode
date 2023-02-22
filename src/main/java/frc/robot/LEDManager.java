package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

// Handles control of the LED strip(s). Do not control manually, use this class.
public class LEDManager {

    public AddressableLED m_led;
    public AddressableLEDBuffer m_ledBuffer;

    public LEDManager(Hardware hardware) {
        m_led = hardware.m_led;
        m_ledBuffer = hardware.m_ledBuffer;
    }

}
