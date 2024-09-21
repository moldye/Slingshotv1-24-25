package org.firstinspires.ftc.teamcode.mechanisms.outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {

    // SLIDES
    private PIDController controller;
    private DcMotorEx outtakeSlide;
    private static double p, i, d; //has to be tuned
    private static double f; //usually mass moved * constant G
    private SlidePositions slidePos;

    // BUCKET
    private Servo bucketFlipAxon;

    public Outtake(HardwareMap hardwareMap, String configName, int direction, double inP, double inI, double inD, double inF){
        outtakeSlide = hardwareMap.get(DcMotorEx.class, configName);
        outtakeSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        if(direction == 0){
            outtakeSlide.setDirection(DcMotorEx.Direction.FORWARD);
        }else{
            outtakeSlide.setDirection(DcMotorEx.Direction.REVERSE);
        }

        controller = new PIDController(p,i,d);

        p = inP; i = inI; d = inD; f = inF;

        slidePos = SlidePositions.BASE_STATE;
    }

    // this is for testing only
    public Outtake(DcMotorEx slidesMotor) {
        this.outtakeSlide = slidesMotor;
    }

    public void moveTicks(int target){
        controller.setPID(p,i,d);
        int pos = outtakeSlide.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlide.setPower(power);
    }
    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }
    public int getPos(){
        return outtakeSlide.getCurrentPosition();
    }

    public void extendToLowBasket() {
        slidePos = SlidePositions.LOW_BASKET;
        moveTicks(30); // tune target obviously
    }

    public void extendToHighBasket() {
        slidePos = SlidePositions.HIGH_BASKET;
        moveTicks(60); // tune target obviously
    }

    public void extendToSpecimenHighRack() {
        slidePos = SlidePositions.SPECIMEN_HIGH_RACK;
        moveTicks(40); // tune target obviously
    }

    public void returnToBaseState() {
        slidePos = SlidePositions.BASE_STATE;
        moveTicks(0); // tune target obviously, this should lock the slides anyway bc of PID
    }

    public enum SlidePositions {
        BASE_STATE,
        LOW_BASKET,
        HIGH_BASKET,
        SPECIMEN_HIGH_RACK
    }
}
