package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.Cycle;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class SlingshotTeleop extends OpMode {
    private GamepadMapping controls;
    private DriveTrain dt;
    private Robot robot;
    private Cycle cycle;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        dt = robot.drivetrain;
        cycle = new Cycle(telemetry, controls, robot);
        robot.hardwareSoftReset();
    }

    @Override
    public void loop() {
        controls.update();
        dt.update();
        cycle.update();
    }
}
