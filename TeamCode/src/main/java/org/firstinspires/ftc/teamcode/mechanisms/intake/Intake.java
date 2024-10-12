package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Intake {
    // motor spins the intake rollers
    // servo that pivots intake
    // servo that pushes samples out back

    // flip down & up intake
    // spin the rollers to intake samples
    // push out samples from back

    // HARDWARE
    // -----------
    private DcMotorEx rollerMotor;
    public Servo pivotAxon;
    public Servo backRollerServo; // set pos to 0.5 to get it to stop
    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon
    //private AnalogInput pivotAnalog;

    // OTHER
    // ----------
    public IntakeConstants.IntakeState intakeState;
    private GamepadMapping controls;
    private Telemetry telemetry;

    // CONTROLS
    // -----------
    private boolean pivotUp; // true if pivot it is initial position (flipped up)
    private boolean extendoIn;
    private double linkageMax = -1;
    private double linkageMin = .325;
    private double linkageThreshold = -.00625; // full extension is -1, just multiplied 1.325 * .25 and subtracted it from min

    public Intake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(Servo.class, "backRoller");
        rightExtendo = hwMap.get(Servo.class, "rightLinkage");
        leftExtendo = hwMap.get(Servo.class, "leftLinkage");
        //pivotAnalog = hwMap.get(AnalogInput.class, "pivotAnalog");

        rollerMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rollerMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);

        intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;

        this.telemetry = telemetry;
        this.controls = controls;

        pivotUp = true; // true initially
        extendoIn = true;
    }

    // This is for testing only :)
    public Intake(DcMotorEx rollerMotor, Servo pivotAxon, Servo backRollerServo, Servo rightExtendo, Servo leftExtendo) {
        this.rollerMotor = rollerMotor;
        this.pivotAxon = pivotAxon; // axon programmer: 0-255, .15-1\
        this.backRollerServo = backRollerServo; // 1 power
        this.rightExtendo = rightExtendo; // -1-.325, min is .325, 0-255
        this.leftExtendo = leftExtendo; // same as above
    }

//    public double calculateFlipWithAnalog() {
//        // get the voltage of our analog line
//        // divide by 3.3 (the max voltage) to get a value between 0 and 1
//        // multiply by 360 to convert it to 0 to 360 degrees
//        // TODO: test this with a class, likely relative encoder
//        double position = pivotAnalog.getVoltage() / 3.3 * 360;
//        return position;
//    }

    public void flipDown() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.INTAKING.pivotPos()); // this will need to be tuned
        pivotUp = false;
    }

    public void flipUp() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos()); // this will need to be tuned
        pivotUp = true;
    }

    public void pushOutSample() {
        // this happens when we're extended
        backRollerServo.setPosition(IntakeConstants.IntakeState.WRONG_ALLIANCE_COLOR_SAMPLE.backRollerPos());
    }
    public void backRollerIdle() {
        backRollerServo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.backRollerPos());}

    public void motorRollerOnForward() {
        rollerMotor.setPower(-1);
    }

    public void transferSample() {
        if (extendoIn && pivotUp) {
            // should push everything out the front of the intake to clear it, both of these values are technically backwards
            rollerMotor.setPower(-1);
            backRollerServo.setPosition(IntakeConstants.IntakeState.TRANSFER.backRollerPos());
        }
    }

    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }

    public void extendoExtend() {
        // TODO this is a button (no need for a retract one)
        // max pos is -1
        // at .325 -> .225
        double newPos = rightExtendo.getPosition() - .1; // * (triggerValue * 10) / 5;
        if (newPos >= linkageMax) {
            rightExtendo.setPosition(newPos);
            leftExtendo.setPosition(newPos);
        } else {
            rightExtendo.setPosition(linkageMax);
            leftExtendo.setPosition(linkageMax);
        }
        extendoIn = false;
    }

    public void extendoFullExtend() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.rLinkagePos()); // obviously tune
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.lLinkagePos());
        extendoIn = false;
    }

    public void extendoFullRetract() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.rLinkagePos()); // obviously tune
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.lLinkagePos());
        extendoIn = true;
    }

    public void resetHardware() {
        rollerMotor.setPower(0);
        rollerMotor.setDirection(DcMotorEx.Direction.FORWARD);

        flipUp();

        backRollerServo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.backRollerPos());

        extendoFullRetract();
    }

    public boolean intakeTooClose(){
        // min = .325, max = -1
        // threshold is -.00625
        if (rightExtendo.getPosition() >= linkageThreshold || leftExtendo.getPosition() >= linkageThreshold) {
            return true;
        }
        return false;
    }

    // TODO Ask James about adding a method for detection of wrong alliance color

    public void update(){
        switch(intakeState){
            case FULLY_RETRACTED:
                // this may automatically start switching to extending?
                if (controls.extend.value()) {
                    intakeState = IntakeConstants.IntakeState.EXTENDING;
                    // may need to add a button lock here? -> should already lock
                } else if (controls.retract.value()){
                    intakeState = IntakeConstants.IntakeState.RETRACTING;
                }
                break;
                // see how fast this is, may need to combine intaking and extending states so the flip down is faster (while we're extending)
            case FULLY_EXTENDED:
                // should come back from pushing out wrong sample and go immediately back to intaking again
                intakeState = IntakeConstants.IntakeState.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case EXTENDING:
                extendoExtend();
                intakeState = IntakeConstants.IntakeState.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case INTAKING:
                flipDown();
                motorRollerOnForward();
                if (controls.retract.value()) {
                    intakeState = IntakeConstants.IntakeState.RETRACTING;
                }
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case RETRACTING:
                flipUp();
                motorRollerOff();
                extendoFullRetract();
                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
                transferSample();
                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
                break;
            case WRONG_ALLIANCE_COLOR_SAMPLE:
                // probably need to do this for some amount of time, test later
                pushOutSample();
                intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
                break;
            case BASE_STATE:
                // reset button pressed and pivot is flipped up
                resetHardware();
                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
                break;
            case TRANSFER:
                // automatically, already verified a right colored sample, rolls it into the bucket
                transferSample();
                break;
        }
        telemetry.addData("intakeState:", intakeState);
    }

    public void updateTelemetry() {
        telemetry.addData("Right Linkage Pos", rightExtendo.getPosition());
        telemetry.addData("Left Linkage Pos", leftExtendo.getPosition());
        // telemetry.addData("Pivot pos (analog):", calculateFlipWithAnalog());
        telemetry.update();
    }

}
