package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.AnalogServo;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ActiveClaw {
    // HARDWARE
    // -----------
    public DcMotorEx rollerMotor;
    public Servo leftExtendo; // axon
    public Servo rightExtendo; // axon

    // OTHER
    // ----------
    private GamepadMapping controls;
    private Telemetry telemetry;
    private long startTime;
}
