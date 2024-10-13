package org.firstinspires.ftc.teamcode.teleop.testers.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class SlowModeTester extends OpMode {
    private GamepadMapping controls;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;
    private DriveTrain dt;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        outtake = robot.outtake;
        dt = robot.drivetrain;
    }

    @Override
    public void loop() {
        dt.update();
        outtake.extendToHighBasket();
    }
}
