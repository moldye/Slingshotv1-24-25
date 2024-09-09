package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp
public class FieldCentricTester extends OpMode {
    private double drive;
    private double strafe;
    private double turn;
    private double currentAngle;

    private Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap);
    }

    @Override
    public void loop() {
        drive = gamepad1.left_stick_y; // y stick is reversed
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
        currentAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

        robot.drivetrain.moveFieldCentric(strafe, drive, turn, currentAngle);

        if (gamepad1.dpad_left) {
            robot.drivetrain.lockHeading(0, currentAngle);
        } else if (gamepad1.dpad_up) {
            robot.drivetrain.lockHeading(90, currentAngle);
        } else if (gamepad1.dpad_right) {
            robot.drivetrain.lockHeading(180, currentAngle);
        } else if (gamepad1.dpad_down) {
            robot.drivetrain.lockHeading(270, currentAngle);
        }

    }
}
