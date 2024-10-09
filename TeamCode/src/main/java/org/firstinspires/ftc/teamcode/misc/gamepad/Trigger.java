package org.firstinspires.ftc.teamcode.misc.gamepad;

public class Trigger {
    private double value;
    private double threshold;

    public Trigger (double initValue, double threshold) {
        value = initValue;
        this.threshold = threshold;
    }

    public void update(double trigger) {
        value = trigger;
    }

    public double getTriggerValue() {
        return value;
    }

    // this is the value that the trigger will be recognized as pressed
    public double getThreshold() {
        return threshold;
    }
}
