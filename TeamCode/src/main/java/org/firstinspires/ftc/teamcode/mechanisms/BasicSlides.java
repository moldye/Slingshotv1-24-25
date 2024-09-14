package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class BasicSlides {
    private PIDController controller;
    private DcMotorEx slide;
    private static double p, i, d; //has to be tuned
    private static double f; //usually mass moved * constant G

    public BasicSlides(HardwareMap hardwareMap, String configName, double inP, double inI, double inD, double inF){
        slide = hardwareMap.get(DcMotorEx.class, configName);
        slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        controller = new PIDController(p,i,d);

        p = inP; i = inI; d = inD; f = inF;
    }

    public void moveTicks(int target){
        controller.setPID(p,i,d);
        int pos = slide.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        slide.setPower(power);
    }
    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }
    public int getPos(){
        return slide.getCurrentPosition();
    }
}