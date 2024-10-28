package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Intake {
    // HARDWARE
    // -----------
    public ActiveIntake activeIntake;
    public ActiveClaw activeClaw;

    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon

    // OTHER
    // ----------
    private GamepadMapping controls;
    private Telemetry telemetry;

    public Intake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        rightExtendo = hwMap.get(Servo.class, "rightLinkage");
        leftExtendo = hwMap.get(Servo.class, "leftLinkage");

        this.telemetry = telemetry;
        this.controls = controls;
    }

    // This is for testing only :)
    public Intake(DcMotorEx rollerMotor, Servo pivotAxon, Servo backRollerServo, Servo rightExtendo, Servo leftExtendo) {
        this.rightExtendo = rightExtendo; // -1-.325, min is .325, 0-255
        this.leftExtendo = leftExtendo; // same as above
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

    public void resetHardware() {
        activeIntake.motorRollerOff();
        activeIntake.rollerMotor.setDirection(DcMotorEx.Direction.REVERSE);

        activeIntake.flipUp();
        activeIntake.backRollerIdle();

        extendoFullRetract();
    }
}
