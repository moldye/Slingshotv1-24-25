package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@TeleOp
public class LockedHeadingPIDTune extends OpMode {
    // ftc dashboard: 192.168.43.1:8080/dash
    private double drive;
    private double strafe;
    private double turn;
    private double currentAngle;

    public static int target = 0;
    public static double p = 0, i = 0, d = 0;

    private Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        telemetry.addData("currentAngle: ", Math.toDegrees(robot.drivetrain.getHeading()));
        currentAngle = robot.drivetrain.getHeading();
    }

    @Override
    public void loop() {
        telemetry.addData("target: ", target); // this while first tuning needs to match targetAngle in drivetrain class

       Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
        currentAngle = robot.drivetrain.getHeading();

        robot.drivetrain.changePID(p,i,d);
        robot.drivetrain.moveFieldCentric(strafe, drive, turn, currentAngle);
        telemetry.addData("position: ", robot.drivetrain.getHeading());

//        if (gamepad1.dpad_left) {
//            robot.drivetrain.lockHeading(0, currentAngle);
//        } else if (gamepad1.dpad_up) {
//            robot.drivetrain.lockHeading(90, currentAngle);
//        } else if (gamepad1.dpad_right) {
//            robot.drivetrain.lockHeading(180, currentAngle);
//        } else if (gamepad1.dpad_down) {
//            robot.drivetrain.lockHeading(270, currentAngle);
//        }

        telemetry.addData("target angle: ", target);
        telemetry.addData("robot error: ", robot.drivetrain.getError());
        telemetry.update();
    }
}
