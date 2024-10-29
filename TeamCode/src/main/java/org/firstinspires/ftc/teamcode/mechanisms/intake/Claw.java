package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Claw {
    // HARDWARE
    public Servo clawServo; // mini axon
    public Servo wrist; // gobilda torque
    public Servo v4b; // max axon (virtual fourbar)

    // OTHER
    private Telemetry telemetry;
    private GamepadMapping controls;
    private ClawConstants.ClawStates clawState = ClawConstants.ClawStates.BASE_STATE;

    public Claw(HardwareMap hardwareMap, Telemetry telemetry, GamepadMapping controls) {
        clawServo = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class, "wrist");
        v4b = hardwareMap.get(Servo.class, "v4b");

        this.telemetry = telemetry;
        this.controls = controls;
    }

    public void moveToTransfer() {
        // v4b at an angle, claw parallel (or close ish)
        clawServo.setPosition(ClawConstants.ClawStates.TRANSFER.getClawPos());
        wrist.setPosition(ClawConstants.ClawStates.TRANSFER.getWristPos());
        v4b.setPosition(ClawConstants.ClawStates.TRANSFER.getV4bPos());
    }

    public void moveToHovering() {
        // v4b at an angle, claw also at an angle (90 angle between v4b and claw)
        clawServo.setPosition(ClawConstants.ClawStates.HOVER_OVER_SAMPLES.getClawPos());
        wrist.setPosition(ClawConstants.ClawStates.HOVER_OVER_SAMPLES.getWristPos());
        v4b.setPosition(ClawConstants.ClawStates.HOVER_OVER_SAMPLES.getV4bPos());
    }

    public void moveToPickingSample() {
        // v4b parallel to ground, claw perpendicular
        clawServo.setPosition(ClawConstants.ClawStates.PICKING_UP_SAMPLE.getClawPos());
        wrist.setPosition(ClawConstants.ClawStates.PICKING_UP_SAMPLE.getWristPos());
        v4b.setPosition(ClawConstants.ClawStates.PICKING_UP_SAMPLE.getV4bPos());

    }

    public void moveToIdle() {
        // claw should be totally straight up (pointing towards ceiling)
        clawServo.setPosition(ClawConstants.ClawStates.BASE_STATE.getClawPos());
        wrist.setPosition(ClawConstants.ClawStates.BASE_STATE.getWristPos());
        v4b.setPosition(ClawConstants.ClawStates.BASE_STATE.getV4bPos());
    }

    public void controlWristPos() {
        double wristSpeed = 1;
        double wristYaw = controls.wristYaw * wristSpeed;
        wristYaw = Math.min(Math.max(wristYaw, 0.0), 1.0);
        wrist.setPosition(wristYaw);
    }

    public void closeClaw() {
        clawServo.setPosition(ClawConstants.ClawStates.BASE_STATE.getClawPos());
    }

    public void openClaw() {
        clawServo.setPosition(ClawConstants.ClawStates.PICKING_UP_SAMPLE.getClawPos());
    }

    public void turnWristToTransfer() {
        wrist.setPosition(ClawConstants.ClawStates.TRANSFER.getWristPos());
    }
}
