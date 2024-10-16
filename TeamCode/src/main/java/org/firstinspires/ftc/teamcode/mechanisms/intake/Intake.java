package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.AnalogServo;
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
    public DcMotorEx rollerMotor;
    public Servo backRollerServo; // set pos to 0.5 to get it to stop
    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon
    public Servo pivotAxon;
    public AnalogServo pivotAnalog;


    // OTHER
    // ----------
    public static IntakeConstants.IntakeState intakeState;
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
        //sets the analogServos PID and stuff
        pivotAnalog = new AnalogServo(pivotAxon, hwMap.get(AnalogInput.class, "pivotAnalog"), 0, 0, 0, 0); //TODO: add tuned PIDF later

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

    public void flipDownFull() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.FULLY_EXTENDED.pivotPos());
        pivotUp = false;
    }

    public void flipDownInitial() {
        // this is so the intake can flip down first past the full pos, then go to full to be ready for intaking
        pivotAxon.setPosition(IntakeConstants.IntakeState.INTAKING.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.INTAKING.pivotPos());
    }

    public void flipUp() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
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
        //if (extendoIn && pivotUp) {
            // should push everything out the front of the intake to clear it, both of these values are technically backwards
            rollerMotor.setPower(1);
            backRollerServo.setPosition(IntakeConstants.IntakeState.TRANSFER.backRollerPos());
        //}
    }

    public void clearIntake() {
        rollerMotor.setPower(-0.5);
        backRollerServo.setPosition(IntakeConstants.IntakeState.TRANSFER.backRollerPos());
    }

    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }

//    public void extendoExtend() {
//        // max pos is -1
//        // at .325 -> .225
//        double newPos = rightExtendo.getPosition() - .1; // * (triggerValue * 10) / 5;
//        if (newPos <= linkageMax) {
//            rightExtendo.setPosition(newPos);
//            leftExtendo.setPosition(newPos);
//        } else {
//            rightExtendo.setPosition(linkageMax);
//            leftExtendo.setPosition(linkageMax);
//        }
//        extendoIn = false;
//    }

    public void extendoFullExtend() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.lLinkagePos());
        extendoIn = false;
    }

    public void extendoFullRetract() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.lLinkagePos());
        extendoIn = true;
    }

    public void extendForOuttake() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.OUTTAKING.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.OUTTAKING.lLinkagePos());
        extendoIn = false;
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
                clearIntakeFailSafe();
                extendoFullRetract();
                // this may automatically start switching to extending?
                if (controls.extend.value()) {
                    intakeState = IntakeConstants.IntakeState.EXTENDING;
                } else if (controls.retract.value()){
                    intakeState = IntakeConstants.IntakeState.RETRACTING;
                }
                break;
                // see how fast this is, may need to combine intaking and extending states so the flip down is faster (while we're extending)
            case FULLY_EXTENDED:
                clearIntakeFailSafe();
                // should come back from pushing out wrong sample and go immediately back to intaking again
                intakeState = IntakeConstants.IntakeState.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case EXTENDING:
                clearIntakeFailSafe();
                extendoFullExtend();
                intakeState = IntakeConstants.IntakeState.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case INTAKING:
                clearIntakeFailSafe();
                flipDownFull();
                motorRollerOnForward();
                if (controls.retract.value()) {
                    intakeState = IntakeConstants.IntakeState.RETRACTING;
                }
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
                }
                break;
            case RETRACTING:
                clearIntakeFailSafe();
                flipUp();
                motorRollerOff();
                extendoFullRetract();
                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
                intakeState = IntakeConstants.IntakeState.TRANSFER;
                break;
            case WRONG_ALLIANCE_COLOR_SAMPLE:
                clearIntakeFailSafe();
                // probably need to do this for some amount of time, test later
                pushOutSample();
                intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
                break;
            case BASE_STATE:
                clearIntakeFailSafe();
                // reset button pressed and pivot is flipped up
                resetHardware();
                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
                break;
            case TRANSFER:
                // pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                clearIntakeFailSafe();
                // automatically, already verified a right colored sample, rolls it into the bucket
                transferSample();
                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
                break;
            case OUTTAKING:
                clearIntakeFailSafe();
                extendForOuttake();
                break;
        }
        telemetry.addData("intakeState:", intakeState);
    }

    public void clearIntakeFailSafe() {
        if (controls.clearIntake.value()) {
            clearIntake();
        } else {
            motorRollerOff();
            backRollerIdle();
        }
    }

    public void updatePivotTrigger() {

    }

    public void updateTelemetry() {
        telemetry.addData("Right Linkage Pos", rightExtendo.getPosition());
        telemetry.addData("Left Linkage Pos", leftExtendo.getPosition());
        // telemetry.addData("Pivot pos (analog):", calculateFlipWithAnalog());
        telemetry.update();
    }

}
