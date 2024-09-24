package org.firstinspires.ftc.teamcode.testers;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.util.gamepad.GamepadMapping;

@TeleOp
public class FieldCentricTester extends OpMode {
    // ftc dashboard: 192.168.43.1:8080/dash
    private double drive;
    private double strafe;
    private double turn;
    private double currentAngle;

    private GamepadMapping controls;

    private Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry, controls);
        telemetry.addData("currentAngle: ", Math.toDegrees(robot.drivetrain.getHeading()));
        currentAngle = robot.drivetrain.getHeading();
    }

    @Override
    public void loop() {
//        drive = gamepad1.left_stick_y;
//        strafe = gamepad1.left_stick_x;
//        turn = gamepad1.right_stick_x;
        currentAngle = robot.drivetrain.getHeading();

        robot.drivetrain.update();
    }
}
