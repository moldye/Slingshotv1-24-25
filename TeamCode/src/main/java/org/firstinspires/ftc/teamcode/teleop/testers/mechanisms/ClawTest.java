package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.template.Claw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class ClawTest extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    private Claw claw;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        claw = new Claw(hardwareMap, telemetry, controls);
    }

    @Override
    public void loop() {
        controls.armDown.update(gamepad1.right_bumper);
        controls.closeClaw.update(gamepad1.left_bumper);

        controls.clawUpdate();
        // this may fight, trying to go between moveToHovering and moveToIdle
        if (controls.armDown.value()) {
            claw.moveToHovering();
            if (controls.closeClaw.value()) {
                claw.moveToPickingSample();
            } else {
                claw.moveToHovering();
            }
        } else {
            claw.moveToIdle();
        }
    }
}
