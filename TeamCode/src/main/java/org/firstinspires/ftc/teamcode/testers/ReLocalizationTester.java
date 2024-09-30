package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.gamepad.GamepadMapping;

public class ReLocalizationTester extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
    }

    @Override
    public void loop() {
//        telemetry.addData("x distance: ", robot.ultraSonics.getSideDistance(robot.getAng()));
//        telemetry.addData("y distance: ", robot.ultraSonics.getBackDistance(robot.getAng()));
//        telemetry.addData("current angle: ", robot.getAng());
    }
}
