package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp
public class FieldCentricTester extends OpMode {
    // ftc dashboard: 192.168.43.1:8080/dash
    private double drive;
    private double strafe;
    private double turn;
    private double currentAngle;

    private Button toggleHeadingLock;
    private GamepadEx driverPad;

    private Robot robot;
    @Override
    public void init() {
        driverPad = new GamepadEx(gamepad1);

        robot = new Robot(hardwareMap, telemetry);
        telemetry.addData("currentAngle: ", Math.toDegrees(robot.drivetrain.getHeading()));
        currentAngle = robot.drivetrain.getHeading();

        toggleHeadingLock = driverPad.getGamepadButton(GamepadKeys.Button.A);
    }

    @Override
    public void loop() {
        drive = gamepad1.left_stick_y; // y stick is reversed
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
        currentAngle = robot.drivetrain.getHeading();

//        if (gamepad1.a) {
//            robot.drivetrain.toggleLockHeadingMode();
//        }

        robot.drivetrain.moveFieldCentric(strafe, drive, turn, currentAngle);

//        if (gamepad1.dpad_left) {
//            robot.drivetrain.lockHeading(0, currentAngle);
//        } else if (gamepad1.dpad_up) {
//            robot.drivetrain.lockHeading(90, currentAngle);
//        } else if (gamepad1.dpad_right) {
//            robot.drivetrain.lockHeading(180, currentAngle);
//        } else if (gamepad1.dpad_down) {
//            robot.drivetrain.lockHeading(270, currentAngle);
//        }


    }
}
