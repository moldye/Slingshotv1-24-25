package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ActiveIntake {
    // HARDWARE
    // -----------
    public DcMotorEx rollerMotor;
    public Servo backRollerServo; // set pos to 0.5 to get it to stop
    public Servo pivotAxon;
    public ColorSensorModule colorSensor;

    // OTHER
    // ----------
    private GamepadMapping controls;
    private Telemetry telemetry;

    public ActiveIntake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        colorSensor = new ColorSensorModule(telemetry, hwMap, true); //just call sensorModule.checkSample() for the color
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(Servo.class, "backRoller");

        rollerMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rollerMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);

        this.telemetry = telemetry;
        this.controls = controls;
    }

    public void flipDownFull() {
        pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_EXTENDED.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.FULLY_EXTENDED.pivotPos());
    }

    public void flipDownToClear() {
        pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.CLEARING.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.CLEARING.pivotPos());
    }

    public void flipUp() {
        pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_RETRACTED.pivotPos());
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
    }

    public void pushOutSample() {
        // this happens when we're extended
        backRollerServo.setPosition(IntakeConstants.ActiveIntakeStates.WRONG_ALLIANCE_COLOR_SAMPLE.backRollerPos());
        motorRollerOnToIntake();
    }
    public void backRollerIdle() {
        backRollerServo.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_RETRACTED.backRollerPos());
    }

    public void motorRollerOnToIntake() {
        rollerMotor.setPower(-1);
    }
    public void motorRollerOff() {
        rollerMotor.setPower(0);
    }
    public void motorRollerOnToClear() { rollerMotor.setPower(0.5); }

    public void transferSample() {
        // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
        backRollerServo.setPosition(IntakeConstants.ActiveIntakeStates.TRANSFER.backRollerPos());
        rollerMotor.setPower(0.53);
    }

    public void transferOff() {
        motorRollerOff();
        backRollerIdle();
    }

    public void clearIntake() {
        motorRollerOnToClear();
        backRollerServo.setPosition(IntakeConstants.ActiveIntakeStates.TRANSFER.backRollerPos());
    }

    public void pivotUpForOuttake() {
        pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.OUTTAKING.pivotPos());
    }

    public void updateTelemetry() {
        telemetry.addData("Pivot pos", pivotAxon.getPosition());
        telemetry.addData("Back Roller Pos: ", backRollerServo.getPosition());
        telemetry.update();
    }
}
