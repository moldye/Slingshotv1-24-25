package org.firstinspires.ftc.teamcode.mechanisms.extendo;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ExtendoSlides {
    // powered by some amount of axon servos, one or two (connect via linkages)
    private Servo rightExtendoAxon;
    private Servo leftExtendoAxon;

    public ExtendoSlides(HardwareMap hwMap) {
        rightExtendoAxon = hwMap.get(Servo.class, "rightExtendo");
        leftExtendoAxon = hwMap.get(Servo.class, "leftExtendo");
    }

    // this is for testing only
    public ExtendoSlides(Servo rightExtendoAxon, Servo leftExtendoAxon) {
        this.rightExtendoAxon = rightExtendoAxon;
        this.leftExtendoAxon = leftExtendoAxon;
    }

    public void extendoExtend() {
        rightExtendoAxon.setPosition(1); // obviously tune
        leftExtendoAxon.setPosition(1); // same here too (also these can be different based on hardware)
    }

    public void extendoRetract() {
        rightExtendoAxon.setPosition(0); // obviously tune
        leftExtendoAxon.setPosition(0); // same here too (also these can be different based on hardware)
    }

}
