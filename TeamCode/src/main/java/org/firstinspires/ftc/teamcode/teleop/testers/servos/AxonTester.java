package org.firstinspires.ftc.teamcode.teleop.testers.servos;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp
public class AxonTester extends OpMode {

    private Robot robot;
    private GamepadMapping controls;
    private Intake intake;

    public static double servoPos = 0;

    public static double rservoPos = .5;
    public static double lservoPos = .5;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
//         intake.rightExtendo.setPosition(.5);
//         intake.leftExtendo.setPosition(.5);
       intake.pivotAxon.setPosition(IntakeConstants.IntakeState.FULLY_RETRACTED.pivotPos());
    }

    @Override
    public void loop() {
        intake.pivotAxon.setPosition(servoPos);
//         intake.rightExtendo.setPosition(servoPos);
//         intake.leftExtendo.setPosition(servoPos);
    }
}