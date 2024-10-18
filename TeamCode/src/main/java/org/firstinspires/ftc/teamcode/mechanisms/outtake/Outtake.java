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
    private OuttakeConstants.SlidePositions slideState;

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

        slideState = OuttakeConstants.SlidePositions.RETRACTED;

        this.telemetry = telemetry;
        this.controls = controls;

    }

    // this is for J-Unit testing only
    public Outtake(DcMotorEx slidesMotorLeft, DcMotorEx slidesMotorRight, Servo bucketServo) {
        this.outtakeSlideLeft = slidesMotorLeft;
        this.outtakeSlideRight = slidesMotorRight;
        this.bucketServo = bucketServo;
    }

    public void moveLeftTicks(double target){
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideLeft.setPower(power);
    }

    public void moveRightTicks(double target){
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideRight.setPower(power);
    }

    public void moveTicks(double target) {
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
        outtakeDTSlow = true;
        moveTicks(OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos()); // tune target obviously
    }

    public void extendToHighBasket() {
        outtakeDTSlow = true;
        moveTicks(OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos()); // tune target obviously
    }

//    public void extendToSpecimenHighRack() {
//        moveTicks(OuttakeConstants.SlidePositions.SPECIMEN_HIGH_RACK;); // tune target obviously
//    }

    public void depositToHP() {
        // this just flips bucket at slide pos 0
        moveTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
        bucketDeposit();
    }

    public void returnToRetracted() {
        moveLeftTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
        moveRightTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
    }

    public void resetHardware() {
        returnToRetracted();
        // other resetting bucket stuff here
        bucketToReadyForTransfer();
    }

    public void resetEncoders() {
        // reset slide motor encoders
        outtakeSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void update() {
        // going to need ElapsedTime likely
        switch(slideState) {
            case RETRACTED:
                updateHang();
                updateOuttakeSlides();
                break;
            case LOW_BASKET:
                updateHang();
                extendToLowBasket();
                // prob need to tune 10 as threshold
                if (controls.flipBucket.value() &&
                        outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos() + 10) {
                    bucketDeposit();
                } else {
                    bucketToReadyForTransfer();
                }
                updateOuttakeSlides();
                break;
            case HIGH_BASKET:
                updateHang();
                extendToHighBasket();
                if (controls.flipBucket.value() &&
                        outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos() + 10) {
                    bucketDeposit();
                } else {
                    bucketToReadyForTransfer();
                }
                updateOuttakeSlides();
                break;
//            case SPECIMEN_HIGH_RACK:
//                extendToSpecimenHighRack();
//                updateOuttakeSlides();
//                break;
            case BASE_STATE:
                slideState = OuttakeConstants.SlidePositions.RETRACTED;
                resetHardware();
                break;
            default:
                // should never be reached since the state shouldn't ever be null
                slideState = OuttakeConstants.SlidePositions.BASE_STATE;
        }

        // TODO add the failsafe code here instead of writing it in each box
        // prob stick updateHang here too (with a condition that excludes base state)
    }

    public static boolean getOuttakeDTSlow() {
        return outtakeDTSlow;
    }

    public void updateOuttakeSlides() {
//        if (controls.botToBaseState.value()) {
//            slideState = OuttakeConstants.SlidePositions.BASE_STATE;
//        }
        if (controls.highBasket.value()) {
            slideState = OuttakeConstants.SlidePositions.HIGH_BASKET;
        } else {
            slideState = OuttakeConstants.SlidePositions.RETRACTED;
            Intake.setIntakeState(IntakeConstants.IntakeState.FULLY_RETRACTED); // this should move the intake back in
        }
        if (controls.lowBasket.value()) {
            slideState = OuttakeConstants.SlidePositions.LOW_BASKET;
        } else {
            slideState = OuttakeConstants.SlidePositions.RETRACTED;
            Intake.setIntakeState(IntakeConstants.IntakeState.FULLY_RETRACTED);
        }
    }

    public void updateHang() {
        if (controls.L1hang.value()) {
            hang();
        } else {
            bucketToReadyForTransfer();
            returnToRetracted();
        }
    }
}
