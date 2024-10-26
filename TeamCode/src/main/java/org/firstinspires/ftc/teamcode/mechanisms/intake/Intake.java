package org.firstinspires.ftc.teamcode.mechanisms.intake;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.AnalogServo;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Intake {
    // HARDWARE
    // -----------
    public DcMotorEx rollerMotor;
    public Servo backRollerServo; // set pos to 0.5 to get it to stop
    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon
    public Servo pivotAxon;
    public AnalogServo pivotAnalog;
    public ColorSensorModule colorSensor;

    // OTHER
    // ----------
    private GamepadMapping controls;
    private Telemetry telemetry;
    private long startTime;

    public Intake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        colorSensor = new ColorSensorModule(telemetry, hwMap, true); //just call sensorModule.checkSample() for the color
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(Servo.class, "backRoller");
        rightExtendo = hwMap.get(Servo.class, "rightLinkage");
        leftExtendo = hwMap.get(Servo.class, "leftLinkage");
        //sets the analogServos PID and stuff
        pivotAnalog = new AnalogServo(pivotAxon, hwMap.get(AnalogInput.class, "pivotAnalog"), 0, 0, 0, 0); //TODO: add tuned PIDF later

        rollerMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rollerMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);

        this.telemetry = telemetry;
        this.controls = controls;
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
    }

    public void halfFlipDownToClear() {
        // this is so the intake can flip down first past the full pos, then go to full to be ready for intaking
        pivotAxon.setPosition(IntakeConstants.IntakeState.CLEARING.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.CLEARING.pivotPos());
    }

    public void flipUp() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
    }

    public void pushOutSample() {
        // this happens when we're extended
        backRollerServo.setPosition(IntakeConstants.IntakeState.WRONG_ALLIANCE_COLOR_SAMPLE.backRollerPos());
        motorRollerOnToIntake();
    }
    public void backRollerIdle() {
        backRollerServo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.backRollerPos());
    }

    public void motorRollerOnToIntake() {
        rollerMotor.setPower(-1);
    }
    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }
    public void motorRollerOnToClear() { rollerMotor.setPower(1); }

    public void transferSample() {
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
        backRollerServo.setPosition(IntakeConstants.IntakeState.TRANSFER.backRollerPos());
        rollerMotor.setPower(0.53);
    }

    public void transferOff() {
        motorRollerOff();
        backRollerIdle();
    }

    public void clearIntake() {
        rollerMotor.setPower(0.5);
        backRollerServo.setPosition(IntakeConstants.IntakeState.TRANSFER.backRollerPos());
    }

    public void extendoFullExtend() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_EXTENDED.lLinkagePos());
    }

    public void extendoFullRetract() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.lLinkagePos());
    }

    public void extendForOuttake() {
        rightExtendo.setPosition(IntakeConstants.IntakeState.OUTTAKING.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.IntakeState.OUTTAKING.lLinkagePos());
    }

    public void pivotUpForOuttake() {
        pivotAxon.setPosition(IntakeConstants.IntakeState.OUTTAKING.pivotPos());
    }

    public void resetHardware() {
        motorRollerOff();
        rollerMotor.setDirection(DcMotorEx.Direction.REVERSE);

        flipUp();
        backRollerIdle();

        extendoFullRetract();
    }

    public void updateTelemetry() {
        telemetry.addData("Right Linkage Pos", rightExtendo.getPosition());
        telemetry.addData("Left Linkage Pos", leftExtendo.getPosition());
        telemetry.addData("Pivot pos", pivotAxon.getPosition());
        telemetry.addData("Back Roller Pos: ", backRollerServo.getPosition());
        telemetry.update();
    }

}
