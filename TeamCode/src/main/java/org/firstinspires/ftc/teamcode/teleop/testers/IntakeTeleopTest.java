package org.firstinspires.ftc.teamcode.teleop.testers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.mechanisms.extendo.Intake;

public class IntakeTeleopTest extends OpMode {

    private Robot robot;
    private GamepadMapping controls;
    private Intake intake;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
    }

    @Override
    public void loop() {
        if (controls.toggleIntakePower.value()) {
            intake.motorRollerOn();
        }
    }
}
