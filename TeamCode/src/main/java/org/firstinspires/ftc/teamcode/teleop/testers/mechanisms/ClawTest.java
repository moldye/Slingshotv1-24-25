package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ClawCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Claw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class ClawTest extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    private ClawCycle cycle;
    private ElapsedTime loopTime;
    private double startTime;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ClawCycle(telemetry, controls, robot);

        robot.intake.claw.resetClaw();

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
    }

    @Override
    public void loop() {
        controls.update();
        robot.drivetrain.update();

        robot.intake.claw.moveToTransfer();
        robot.intake.claw.turnWristToTransfer();
        if (loopTime.milliseconds() - startTime > 10000){
            robot.intake.claw.openClaw();
        }

        telemetry.addData("loop time - start time", loopTime.milliseconds() - startTime);
    }
}
