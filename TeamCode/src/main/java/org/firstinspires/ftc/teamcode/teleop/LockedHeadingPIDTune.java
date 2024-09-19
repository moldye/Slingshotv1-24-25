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
    private Robot robot;
    public static double target;
    public static double p = 0.04, i = 0, d = 0.003, f = 0.00001;

    private Telemetry dashboardTelemetry;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        telemetry.addData("currentAngle: ", Math.toDegrees(robot.drivetrain.getHeading()));
        currentAngle = robot.drivetrain.getHeading();
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        target = 0;
    }

    @Override
    public void loop() {
        dashboardTelemetry.addData("target angle: ", target);

        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
        currentAngle = robot.drivetrain.getHeading();

        // this is relative to the driver, also angles get wrapped in lockHeading()
        if (gamepad1.dpad_left) {
            robot.drivetrain.setTargetAngle(0);
        } else if (gamepad1.dpad_up) {
            robot.drivetrain.setTargetAngle(90);
        } else if (gamepad1.dpad_right) {
            robot.drivetrain.setTargetAngle(180);
        } else if (gamepad1.dpad_down) {
            robot.drivetrain.setTargetAngle(270);
        }

        robot.drivetrain.changePID(p,i,d,f);
        robot.drivetrain.moveFieldCentric(strafe, drive, turn, currentAngle);

        dashboardTelemetry.addData("current heading: ", robot.drivetrain.getHeading());
        dashboardTelemetry.update();
    }
}
