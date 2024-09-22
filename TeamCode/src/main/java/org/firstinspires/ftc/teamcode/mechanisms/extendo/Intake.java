package org.firstinspires.ftc.teamcode.mechanisms.extendo;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
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

    private DcMotorEx rollerMotor;
    private Servo pivotAxon;
    private CRServo backRollerServo;
    private Servo leftExtendo;
    private Servo rightExtendo;

    private IntakeConstants.IntakeStates intakeState;

    private GamepadMapping controls;
    private Telemetry telemetry;

    public Intake(HardwareMap hwMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(CRServo.class, "backRollerServo");
        rightExtendo = hwMap.get(Servo.class, "rightExtendo"); // both these servos axons
        leftExtendo = hwMap.get(Servo.class, "leftExtendo");

        rollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rollerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);

        backRollerServo.setDirection(DcMotorSimple.Direction.REVERSE); // reverse for servo to push samples out -> tune

        intakeState = IntakeConstants.IntakeStates.FULLY_RETRACTED;

        this.telemetry = telemetry;
        controls = new GamepadMapping(gamepad1, gamepad2);
    }

    // This is for testing only :)
    public Intake(DcMotorEx rollerMotor, Servo pivotAxon, CRServo backRollerServo, Servo rightExtendo, Servo leftExtendo) {
        this.rollerMotor = rollerMotor;
        this.pivotAxon = pivotAxon;
        this.backRollerServo = backRollerServo;
        this.rightExtendo = rightExtendo;
        this.leftExtendo = leftExtendo;
    }

    public void flipDown() {
        pivotAxon.setPosition(1); // this will need to be tuned
    }

    public void flipUp() {
        pivotAxon.setPosition(0); // this will need to be tuned
    }

    public void pushOutSample() {
        backRollerServo.setPower(1);
    }

    public void motorRollerOn() {
        rollerMotor.setPower(1); // want this to be as fast as possible to intake well
    }

    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }

    public void extendoExtend() {
        rightExtendo.setPosition(1); // obviously tune
        leftExtendo.setPosition(1); // same here too (also these can be different based on hardware)
    }

    public void extendoRetract() {
        rightExtendo.setPosition(0); // obviously tune
        leftExtendo.setPosition(0); // same here too (also these can be different based on hardware)
    }

    public void resetHardware() {
        rollerMotor.setPower(0);
        rollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        pivotAxon.setPosition(0);
        pivotAxon.setDirection(Servo.Direction.FORWARD);

        backRollerServo.setPower(0);
        backRollerServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // TODO Ask James about adding a method for detection of wrong alliance color

    public void update(){
        switch(intakeState){
            case FULLY_RETRACTED:
                // this will need to be done also when we come back in from fully extended
                if (controls.switchExtendo.value()) {
                    intakeState = IntakeConstants.IntakeStates.EXTENDING;
                    // may need to add a button lock here? -> should already lock
                } else {
                    intakeState = IntakeConstants.IntakeStates.RETRACTING;
                }
                break;
                // see how fast this is, may need to combine intaking and extending states so the flip down is faster (while we're extending)
            case FULLY_EXTENDED:
                // should come back from pushing out wrong sample and go immediately back to intaking again
                intakeState = IntakeConstants.IntakeStates.INTAKING;
                break;
            case EXTENDING:
                extendoExtend();
                intakeState = IntakeConstants.IntakeStates.INTAKING;
                break;
            case INTAKING:
                flipDown();
                motorRollerOn();
                if (!controls.switchExtendo.value()) {
                    intakeState = IntakeConstants.IntakeStates.RETRACTING;
                }
                break;
            case RETRACTING:
                flipUp();
                motorRollerOff();
                extendoRetract();
                intakeState = IntakeConstants.IntakeStates.FULLY_RETRACTED;
                break;
            case WRONG_ALLIANCE_COLOR_SAMPLE:
                // probably need to do this for some amount of time, test later
                pushOutSample();
                intakeState = IntakeConstants.IntakeStates.FULLY_EXTENDED;
                break;
        }
        telemetry.addData("intakeState:", intakeState);
    }

}
