package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.extendo.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.util.gamepad.GamepadMapping;

@TeleOp
public class SlingshotTeleop extends OpMode {
    private GamepadMapping controls;
    private DriveTrain dt;
    private Intake intake;
    private Outtake outtake;
    private Robot robot;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry);
        dt = new DriveTrain(hardwareMap, robot.imu, telemetry);
        intake = new Intake(hardwareMap, telemetry, gamepad1, gamepad2);
        outtake = new Outtake(hardwareMap, 0, 0, 0, 0, 0, telemetry, gamepad1, gamepad2); // tune PID values
    }

    @Override
    public void loop() {
        controls.update();
        dt.update();
        intake.update();
        outtake.update();
    }
}
