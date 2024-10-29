package org.firstinspires.ftc.teamcode.teleop.testers.servos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ClawServoTests extends OpMode {
    private Robot robot;
    private GamepadMapping controls;

    public static double v4bPos = 0; // axon max
    public static double wristPos = 0; // gobilda torque
    public static double clawPos = 0; // axon mini

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        robot.intake.claw.clawServo.setPosition(0);
        robot.intake.claw.wrist.setPosition(0);
        robot.intake.claw.v4b.setPosition(0);

    }

    @Override
    public void loop() {
        robot.intake.claw.clawServo.setPosition(clawPos);
        robot.intake.claw.wrist.setPosition(wristPos);
        robot.intake.claw.v4b.setPosition(v4bPos);
    }
}
