package org.firstinspires.ftc.teamcode.mechanisms.outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Outtake {

    // extendo out a bit 4-5 in
    // lift up and tilt a bit to keep sample in
    // one button for high bar one button for low bar

    // SLIDES
    private PIDController controller;
    private DcMotorEx outtakeSlideRight;
    private DcMotorEx outtakeSlideLeft;
    private static double p, i, d; //has to be tuned
    private static double f; // usually mass moved * constant G
    private OuttakeConstants.SlidePositions slideState;
    private int numOuttakeButtonPressed = 0;

    // BUCKET
    public Servo bucketServo;

    // OTHER
    Telemetry telemetry;
    GamepadMapping controls;
    private boolean outtakeDTSlow = false;

    OuttakeConstants outtakeState;

    public Outtake(HardwareMap hardwareMap, int direction, double inP, double inI, double inD, double inF, Telemetry telemetry,
    GamepadMapping controls){
        outtakeSlideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
        outtakeSlideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        outtakeSlideLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        outtakeSlideRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        bucketServo = hardwareMap.get(Servo.class, "bucketServo");

        if(direction == 0){
            outtakeSlideLeft.setDirection(DcMotorEx.Direction.FORWARD);
            outtakeSlideRight.setDirection(DcMotorEx.Direction.REVERSE);
        }else{
            outtakeSlideLeft.setDirection(DcMotorEx.Direction.REVERSE);
            outtakeSlideRight.setDirection(DcMotorEx.Direction.FORWARD);
        }

        controller = new PIDController(p,i,d);

        p = inP; i = inI; d = inD; f = inF;

        slideState = OuttakeConstants.SlidePositions.RETRACTED;

        this.telemetry = telemetry;
        this.controls = controls;

    }

    // this is for J-Unit testing only
    public Outtake(DcMotorEx slidesMotorLeft, DcMotorEx slidesMotorRight, Servo bucketServo) {
        this.outtakeSlideLeft = slidesMotorLeft;
        this.outtakeSlideRight = slidesMotorRight;
        // this.bucketServo = bucketServo;
    }

    public void moveLeftTicks(int target){
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideLeft.setPower(power);
    }

    public void moveRightTicks(int target){
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideRight.setPower(power);
    }

    public void moveTicks(int target) {
        moveRightTicks(target);
        moveLeftTicks(target);
    }

    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }

    // so this should be the same for both motors?
    public int getPos(){
        return outtakeSlideLeft.getCurrentPosition();
    }

    public void extendToLowBasket() {
        // slideState = OuttakeConstants.SlidePositions.LOW_BASKET;
        moveLeftTicks(30); // tune target obviously
        moveRightTicks(30);
    }

    public void extendToHighBasket() {
        // slideState = OuttakeConstants.SlidePositions.HIGH_BASKET;
        moveLeftTicks(60); // tune target obviously
        moveRightTicks(60);
    }

    public void extendToSpecimenHighRack() {
        // slideState = OuttakeConstants.SlidePositions.SPECIMEN_HIGH_RACK;
        moveLeftTicks(40); // tune target obviously
        moveRightTicks(40);
    }

    public void returnToRetracted() {
        // slideState = OuttakeConstants.SlidePositions.RETRACTED; // this should actually just get desired pos ticks when we tune (enum stuff)
        moveLeftTicks(0); // tune target obviously, this should lock the slides anyway bc of PID
        moveRightTicks(0);
    }

    public void resetHardware() {
        returnToRetracted();
        // other resetting bucket stuff here
    }

    public void resetEncoders() {
        // reset slide motor encoders
        outtakeSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void bucketToReadyForTransfer() {
        bucketServo.setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getBucketPos());
    }

    public void bucketDeposit() {
        bucketServo.setPosition(OuttakeConstants.BucketPositions.DEPOSIT.getBucketPos());
    }


    public void update() {
        switch(slideState) {
            case RETRACTED:
                updateOuttakeSlides();
                break;
            case LOW_BASKET:
                extendToLowBasket();
                updateOuttakeSlides();
                break;
            case HIGH_BASKET:
                extendToHighBasket();
                updateOuttakeSlides();
                break;
            case SPECIMEN_HIGH_RACK:
                extendToSpecimenHighRack();
                updateOuttakeSlides();
                break;
            case BASE_STATE:
                resetHardware();
                slideState = OuttakeConstants.SlidePositions.RETRACTED;
                break;
        }
    }

    public boolean getOuttakeDTSlow() {
        return outtakeDTSlow;
    }

    public void updateOuttakeSlides() {
        if (controls.botToBaseState.value()) {
            slideState = OuttakeConstants.SlidePositions.BASE_STATE;
        }
        if (controls.resetSlides.value()) {
            returnToRetracted();
            slideState = OuttakeConstants.SlidePositions.RETRACTED;
        }
        if (controls.outtakeSlidesButton) {
            outtakeDTSlow = true;
            numOuttakeButtonPressed += 1;
        }
        if (numOuttakeButtonPressed == 1) {
            slideState = OuttakeConstants.SlidePositions.LOW_BASKET;
        } else if (numOuttakeButtonPressed == 2) {
            slideState = OuttakeConstants.SlidePositions.HIGH_BASKET;
        } else if (numOuttakeButtonPressed == 3) {
            slideState = OuttakeConstants.SlidePositions.SPECIMEN_HIGH_RACK;
            numOuttakeButtonPressed = 0;
        }
    }
}
