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

    public void LEDCone() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 222, 255, 13);
         }
         
         m_led.setData(m_ledBuffer);
    }

    public void LEDCube() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 190, 13, 255);
         }
         
         m_led.setData(m_ledBuffer);
    }
}
