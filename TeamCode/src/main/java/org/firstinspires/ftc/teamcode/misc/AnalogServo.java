package org.firstinspires.ftc.teamcode.misc;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class AnalogServo implements Servo {
    AnalogInput analogInput;

    private PIDController controller;
    private double p = 0, i = 0, d = 0; //has to be tuned
    private double f = 0;


    public double powerToPosition(double power){
        return power*2-1;
    }

    public void setPower(double power){
          this.setPosition(powerToPosition(power));
    }

    public void runToPos(double targetAngle){
        controller.setPID(p,i,d);
        double pos = this.getPosition();
        double pid = controller.calculate(pos, targetAngle);
        double ff = Math.cos(Math.toRadians(targetAngle)) * f;
        double power = pid + ff;

        this.setPosition(powerToPosition(power));
    }

    public double getPosition(){
        return analogInput.getVoltage() / 3.3 * 360;
    }

    public void init(AnalogInput analogInput, double p, double i, double d, double f){
        this.analogInput = analogInput;
        this.p = p; this.i = i; this.d = d; this.f = f;
    }

    public void changePIDF(double p, double i, double d, double f){
        this.p = p; this.i = i; this.d = d; this.f = f;
    }

}
