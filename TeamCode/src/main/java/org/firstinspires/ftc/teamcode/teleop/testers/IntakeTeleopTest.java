package org.firstinspires.ftc.teamcode.teleop.testers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;

@Config
@TeleOp
public class IntakeTeleopTest extends OpMode {

    // to extend, extend out slides a little bit then pivot down
    private Robot robot;
    private GamepadMapping controls;
    private Intake intake;
    private DriveTrain dt;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        dt = robot.drivetrain;
    }

    @Override
    public void loop() {
        controls.pivot.update(gamepad1.a);
        controls.extend.update(gamepad1.left_trigger);
        controls.retract.update(gamepad1.right_trigger);
        controls.powerIntake.update(gamepad1.x);

        intake.updateTelemetry();
        controls.joystickUpdate();
        dt.update();

        if(controls.extend.getTriggerValue() > controls.extend.getThreshold()) {
            intake.extendoExtend(controls.extend.getTriggerValue());
            intake.motorRollerOnBackwards();
            if (controls.pivot.value()) {
                intake.motorRollerOnForward();
                intake.flipDown();
            } else {
                intake.flipUp();
                intake.motorRollerOff();
            }
            if (controls.powerIntake.value()) {
                intake.motorRollerOff();
            } else {
                intake.motorRollerOnForward();
            }
        }
        if(controls.retract.getTriggerValue() > controls.retract.getThreshold()) {
            intake.flipUp();
            intake.extendoRetract(controls.retract.getTriggerValue());
            intake.motorRollerOff();
        }
    }
}
