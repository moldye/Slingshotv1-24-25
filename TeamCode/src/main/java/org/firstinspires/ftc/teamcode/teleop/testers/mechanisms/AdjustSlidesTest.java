package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class AdjustSlidesTest extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    private Outtake outtake;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        outtake = robot.outtake;
    }

    @Override
    public void loop() {
        controls.update();
        robot.drivetrain.update();
        outtake.adjustSlides();
    }
}
