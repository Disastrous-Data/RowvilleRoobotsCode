package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

// Handles control of the LED strip(s). Do not control manually, use this class.
public class LEDManager {

    public AddressableLED m_led;
    public AddressableLEDBuffer m_ledBuffer;

    private int m_rainbowFirstPixelHue = 0;

    public LEDManager(Hardware hardware) {
        m_led = hardware.m_led;
        m_ledBuffer = hardware.m_ledBuffer;
    }

    public void setledmode(boolean LEDState) {
        if (LEDState) {
            LEDCone();
        } else {
            LEDCube();
        }
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
    public void LEDRainbow() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
            // Set the value
            m_ledBuffer.setHSV(i, hue, 255, 128);
          }
          // Increase by to make the rainbow "move"
          m_rainbowFirstPixelHue += 3;
          // Check bounds
          m_rainbowFirstPixelHue %= 180;
    }
}

