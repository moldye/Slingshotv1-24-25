package org.firstinspires.ftc.teamcode.mechanisms.outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Outtake {
    // SLIDES
    private PIDController controller;
    public DcMotorEx outtakeSlideRight;
    public DcMotorEx outtakeSlideLeft;
    private static double p, i, d; //has to be tuned
    private static double f; // usually mass moved * constant G
    // BUCKET
    public Servo bucketServo;

    // OTHER
    Telemetry telemetry;
    GamepadMapping controls;
    private static boolean outtakeDTSlow = false;

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

        this.telemetry = telemetry;
        this.controls = controls;
    }

    // this is for J-Unit testing only
    public Outtake(DcMotorEx slidesMotorLeft, DcMotorEx slidesMotorRight, Servo bucketServo) {
        this.outtakeSlideLeft = slidesMotorLeft;
        this.outtakeSlideRight = slidesMotorRight;
        this.bucketServo = bucketServo;
    }

    public void moveTicks(double target) {
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideRight.setPower(power);
        outtakeSlideLeft.setPower(power);
    }

    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }

    // so this should be the same for both motors?
    public int getPos(){
        return outtakeSlideLeft.getCurrentPosition();
    }

    public void extendToLowBasket() {
        outtakeDTSlow = true;
        moveTicks(OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos());
    }

    public void extendToHighBasket() {
        outtakeDTSlow = true;
        moveTicks(OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos());
    }

    public void extendToSpecimenHighRack() {
        moveTicks(OuttakeConstants.SlidePositions.SPECIMEN_HIGH_RACK.getSlidePos()); // tune target obviously
    }

    public void depositToHP() {
        // this just flips bucket at slide pos 0
        moveTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
        bucketDeposit();
    }

    public void returnToRetracted() {
        outtakeDTSlow = false;
        moveTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
    }

    public void resetHardware() {
        outtakeDTSlow = false;
        returnToRetracted();
        // other resetting bucket stuff here
        bucketToReadyForTransfer();
    }

    public void resetEncoders() {
        // reset slide motor encoders
        outtakeSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void bucketToReadyForTransfer() {
        bucketServo.setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getBucketPos());
    }

    public void bucketDeposit() {
        bucketServo.setPosition(OuttakeConstants.BucketPositions.DEPOSIT.getBucketPos());
    }

    public void bucketTilt() {
        bucketServo.setPosition(OuttakeConstants.BucketPositions.TRANSFERING.getBucketPos());
    }

    public void hang() {
        moveTicks(OuttakeConstants.SlidePositions.HANG.getSlidePos());
        bucketDeposit();
    }

    public void setMotorsToTeleOpMode() {
        outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static boolean getOuttakeDTSlow() {
        return outtakeDTSlow;
    }

    public void adjustSlides() {
        // right side is strung
        double currPos = outtakeSlideRight.getCurrentPosition();
        double adjustTicks = currPos + controls.adjustmentSlides * 20; // tune 20
        if (adjustTicks >= OuttakeConstants.SlidePositions.RETRACTED.getSlidePos() &&
                adjustTicks <= OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos()) {
            moveTicks(outtakeSlideLeft.getCurrentPosition() + adjustTicks);
        }
    }

    public void updateTelemetry() {
        telemetry.addData("bucket servo pos: ", bucketServo.getPosition());
        telemetry.addData("right motor pos/ticks: ", outtakeSlideRight.getCurrentPosition());
        telemetry.addData("left motor pos/ticks: ", outtakeSlideLeft.getCurrentPosition());
    }
}
