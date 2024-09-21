package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

public class ReLocalizationTester extends OpMode {
    private Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
//        telemetry.addData("x distance: ", robot.ultraSonics.getSideDistance(robot.getAng()));
//        telemetry.addData("y distance: ", robot.ultraSonics.getBackDistance(robot.getAng()));
//        telemetry.addData("current angle: ", robot.getAng());
    }
}
