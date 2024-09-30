package org.firstinspires.ftc.teamcode.mechanisms.extendo;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.gamepad.GamepadMapping;

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
    private Servo pivotAxon;
    private Servo backRollerServo;
    private Servo leftExtendo;
    private Servo rightExtendo;

    // OTHER
    // ----------
    private IntakeConstants.IntakeStates intakeState;
    private GamepadMapping controls;
    private Telemetry telemetry;

    // CONTROLS
    // -----------
    private boolean pivotUp; // true if pivot it is initial position (flipped up)
    private boolean extendoIn;

    public Intake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(Servo.class, "backRollerServo");
        rightExtendo = hwMap.get(Servo.class, "rightExtendo"); // both these servos axons
        leftExtendo = hwMap.get(Servo.class, "leftExtendo");

        rollerMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rollerMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);


        intakeState = IntakeConstants.IntakeStates.FULLY_RETRACTED;

        this.telemetry = telemetry;
        this.controls = controls;

        pivotUp = true; // true initially
        extendoIn = true;
    }

    // This is for testing only :)
    public Intake(DcMotorEx rollerMotor, Servo pivotAxon, Servo backRollerServo, Servo rightExtendo, Servo leftExtendo) {
        this.rollerMotor = rollerMotor;
        this.pivotAxon = pivotAxon;
        this.backRollerServo = backRollerServo;
        this.rightExtendo = rightExtendo;
        this.leftExtendo = leftExtendo;
    }

    public void flipDown() {
        pivotAxon.setPosition(1); // this will need to be tuned
        pivotUp = false;
    }

    public void flipUp() {
        pivotAxon.setPosition(0); // this will need to be tuned
        pivotUp = true;
    }

    public void pushOutSample() {
        backRollerServo.setPosition(1); // tune wether it should be pos 1 or 0 to run which way
    }

    public void motorRollerOn() {
        rollerMotor.setPower(1); // want this to be as fast as possible to intake well
    }

    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }

    public void extendoExtend() {
        rightExtendo.setPosition(1); // obviously tune
        leftExtendo.setPosition(0); // same here too (also these can be different based on hardware)
        extendoIn = false;
    }

    public void extendoRetract() {
        rightExtendo.setPosition(0); // obviously tune
        leftExtendo.setPosition(1); // same here too (also these can be different based on hardware)
        extendoIn = true;
    }

    public void resetHardware() {
        rollerMotor.setPower(0);
        rollerMotor.setDirection(DcMotorEx.Direction.FORWARD);

        flipUp();

        backRollerServo.setPosition(0.5);

        extendoRetract();
    }

    public void transferSample() {
        if (extendoIn && pivotUp) {
            // run roller motor backwards
            rollerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            rollerMotor.setPower(1);
        }
    }

    // TODO Ask James about adding a method for detection of wrong alliance color

    public void update(){
        switch(intakeState){
            case FULLY_RETRACTED:
                // this may automatically start switching to extending?
                if (controls.switchExtendo.value()) {
                    intakeState = IntakeConstants.IntakeStates.EXTENDING;
                    // may need to add a button lock here? -> should already lock
                } else if (!controls.switchExtendo.value()){
                    intakeState = IntakeConstants.IntakeStates.RETRACTING;
                }
                break;
                // see how fast this is, may need to combine intaking and extending states so the flip down is faster (while we're extending)
            case FULLY_EXTENDED:
                // should come back from pushing out wrong sample and go immediately back to intaking again
                intakeState = IntakeConstants.IntakeStates.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeStates.BASE_STATE;
                }
                break;
            case EXTENDING:
                extendoExtend();
                intakeState = IntakeConstants.IntakeStates.INTAKING;
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeStates.BASE_STATE;
                }
                break;
            case INTAKING:
                flipDown();
                motorRollerOn();
                if (!controls.switchExtendo.value()) {
                    intakeState = IntakeConstants.IntakeStates.RETRACTING;
                }
                if (controls.botToBaseState.value() && pivotUp) {
                    intakeState = IntakeConstants.IntakeStates.BASE_STATE;
                }
                break;
            case RETRACTING:
                flipUp();
                motorRollerOff();
                extendoRetract();
                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
                transferSample();
                intakeState = IntakeConstants.IntakeStates.FULLY_RETRACTED;
                break;
            case WRONG_ALLIANCE_COLOR_SAMPLE:
                // probably need to do this for some amount of time, test later
                pushOutSample();
                intakeState = IntakeConstants.IntakeStates.FULLY_EXTENDED;
                break;
            case BASE_STATE:
                // reset button pressed and pivot is flipped up
                resetHardware();
                intakeState = IntakeConstants.IntakeStates.FULLY_RETRACTED;
                break;
            case TRANSFER:
                // automatically, if right colored sample, rolls it into the bucket
                transferSample();
                break;
        }
        telemetry.addData("intakeState:", intakeState);
    }

}
