package org.firstinspires.ftc.teamcode.teleop.testers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp
public class TransferTest extends OpMode {
    // to extend, extend out slides a little bit then pivot down
    private Robot robot;
    private GamepadMapping controls;
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

        controls.pivot.update(gamepad1.a);
        controls.switchExtendo.update(gamepad1.b);
        controls.powerIntake.update(gamepad1.x);
        controls.pushOutSample.update(gamepad1.y);

        controls.joystickUpdate();
        dt.update();

        if (controls.switchExtendo.value()) {
            // intake.intakeState.setExtendLinkagePositions(rExLinkagePos, lExLinkagePos);
            intake.extendoFullExtend();
            if (controls.pivot.value()) {
                intake.flipDown();
            } else {
                intake.flipUp();
            }
            if (controls.powerIntake.value()) {
                intake.motorRollerOff();
            } else {
                intake.motorRollerOnForward();
            }
        } else {
            intake.flipUp();
            intake.motorRollerOff();
            // intake.intakeState.setRetractLinkagePositions(rRLinkagePos, lRLinkagePos);
            intake.extendoFullRetract();
            if (controls.pushOutSample.value()) {
                intake.pushOutSample();
            } else {
                intake.backRollerIdle();
            }
        }
        intake.updateTelemetry();

        // this is testing gradual extendo
//        if(controls.extend.getTriggerValue() > controls.extend.getThreshold()) {
//            intake.extendoExtend(controls.extend.getTriggerValue());
//        }
//        if(controls.retract.getTriggerValue() > controls.retract.getThreshold()) {
//            intake.extendoRetract(controls.retract.getTriggerValue());
//            if (intake.intakeTooClose()) {
//                intake.flipUp();
//            }
//        }
    }
}
