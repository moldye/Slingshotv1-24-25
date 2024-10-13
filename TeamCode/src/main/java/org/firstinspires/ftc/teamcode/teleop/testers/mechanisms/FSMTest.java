package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class FSMTest extends OpMode {
    private GamepadMapping controls;
    private DriveTrain dt;
    private Intake intake;
    private Outtake outtake;
    private Robot robot;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
    }

    @Override
    public void loop() {
        controls.update();
        // NOT ACTUALLY GOING TO DO THIS IN REAL TELEOP, THIS IS FOR TESTING ONLY
        robot.resetHardware();
        robot.update();
    }
}
