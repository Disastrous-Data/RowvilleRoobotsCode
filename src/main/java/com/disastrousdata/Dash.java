/*
Dash.java
Written by CoPokBl

This is a wrapper for SmartDashboard or whatever we end up using.
It's so we don't have to change all the references to SmartDashboard
if we end up using something else.
*/

package com.disastrousdata;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dash {

    public static void set(String key, String value) {
        SmartDashboard.putString(key, value);
    }

    public static void set(String key, double value) {
        SmartDashboard.putNumber(key, value);
    }

    public static void set(String key, boolean value) {
        SmartDashboard.putBoolean(key, value);
    }

    public static double get(String key) {
        return SmartDashboard.getNumber(key, 0);
    }

    public static void putData(String key, Sendable value) {
        SmartDashboard.putData(key, value);
    }
}
