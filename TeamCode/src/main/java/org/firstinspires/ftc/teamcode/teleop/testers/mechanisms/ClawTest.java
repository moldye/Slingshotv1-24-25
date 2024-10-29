package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ClawCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Claw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class ClawTest extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    private ClawCycle cycle;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ClawCycle(telemetry, controls, robot);
    }

    @Override
    public void loop() {
        controls.clawUpdate();
        cycle.clawIntakeUpdate();
    }
}
