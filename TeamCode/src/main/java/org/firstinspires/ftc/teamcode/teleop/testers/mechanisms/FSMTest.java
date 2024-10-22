package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.Cycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class FSMTest extends OpMode {
    private GamepadMapping controls;
    private Cycle cycle;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new Cycle(telemetry, controls, robot);

        intake = robot.intake;
        outtake = robot.outtake;

        robot.outtake.resetEncoders();

        robot.intake.resetHardware();
        robot.outtake.resetHardware();
    }

    @Override
    public void loop() {
        // already does dt & controls.update();
        cycle.update();
    }
}
