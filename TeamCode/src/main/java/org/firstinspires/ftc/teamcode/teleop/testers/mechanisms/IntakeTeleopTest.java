package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

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
        controls.switchExtendo.update(gamepad1.b);
        controls.powerIntake.update(gamepad1.x);
        controls.transfer.update(gamepad1.y);

        controls.joystickUpdate();
        dt.update();

        if (controls.switchExtendo.value()) {
            // intake.intakeState.setExtendLinkagePositions(rExLinkagePos, lExLinkagePos);
            intake.extendoFullExtend();
            if (controls.pivot.value()) {
                intake.flipDownFull();
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
            if (controls.transfer.value()) {
                intake.transferSample();
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
