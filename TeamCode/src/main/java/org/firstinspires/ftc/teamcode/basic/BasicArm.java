package org.firstinspires.ftc.teamcode.basic;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BasicArm {
    private PIDController controller;
    private DcMotorEx arm;
    private double p, i, d; //has to be tuned
    private double f;

    private double ticksPerDegree = 0;



    public BasicArm(HardwareMap hardwareMap, String configName, double inP, double inI, double inD, double inF, double tickDegree){
        arm = hardwareMap.get(DcMotorEx.class, configName);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        controller = new PIDController(p,i,d);

        p = inP; i = inI; d = inD; f = inF;

        ticksPerDegree = tickDegree;
    }

    public void moveTicks(int target){
        controller.setPID(p,i,d);
        int pos = arm.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double ff = Math.cos(Math.toRadians(target/ticksPerDegree)) * f;
        double power = pid + ff;
        arm.setPower(power);
    }
    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }
    public int getPos(){
       return arm.getCurrentPosition();
    }
}