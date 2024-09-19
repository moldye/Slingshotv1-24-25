package org.firstinspires.ftc.teamcode.mechanisms.specimen;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecimenClaw {
    private Servo clawServo;

    public SpecimenClaw(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "clawServo");
    }
    // this is for testing only
    public SpecimenClaw(Servo clawServo) {
        this.clawServo = clawServo;
    }

    public void closeClaw() {
        clawServo.setPosition(0); // obviously tune later
    }

    public void openClaw() {
        clawServo.setPosition(1); // obviously tune later
    }
}
