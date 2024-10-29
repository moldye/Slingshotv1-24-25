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
    public v4bActive v4bActiveIntake;
    public Claw claw;

    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon

    // OTHER
    // ----------
    private GamepadMapping controls;
    private Telemetry telemetry;

    public Intake(HardwareMap hwMap, Telemetry telemetry, GamepadMapping controls) {
        activeIntake = new ActiveIntake(hwMap, telemetry, controls);
        v4bActiveIntake = new v4bActive(hwMap, telemetry, controls);
        claw = new Claw(hwMap, telemetry, controls);

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
        rightExtendo.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_EXTENDED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_EXTENDED.lLinkagePos());
    }

    public void extendoFullRetract() {
        rightExtendo.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_RETRACTED.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.ActiveIntakeStates.FULLY_RETRACTED.lLinkagePos());
    }

    public void extendForOuttake() {
        rightExtendo.setPosition(IntakeConstants.ActiveIntakeStates.OUTTAKING.rLinkagePos());
        leftExtendo.setPosition(IntakeConstants.ActiveIntakeStates.OUTTAKING.lLinkagePos());
    }

    public void resetHardware() {
        activeIntake.motorRollerOff();
        activeIntake.rollerMotor.setDirection(DcMotorEx.Direction.REVERSE);

        activeIntake.flipUp();
        activeIntake.backRollerIdle();

        extendoFullRetract();
    }
}
