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
        intake.extendoFullRetract();
    }

    @Override
    public void loop() {
        controls.pivot.update(gamepad1.a);
        controls.switchExtendo.update(gamepad1.b);
        controls.powerIntake.update(gamepad1.x);
        controls.transfer.update(gamepad1.y);
        controls.deposit.update(gamepad1.left_bumper);
        controls.flipBucket.update(gamepad1.right_bumper);

        controls.joystickUpdate();
        dt.update();

        if (controls.switchExtendo.value()) {
            intake.extendoFullExtend();
            if (controls.pivot.value()) {
                intake.flipDownFull();
            } else {
                intake.flipUp();
            }
            if (controls.transfer.value()) {
                intake.pushOutSample();
            } else {
                intake.backRollerIdle();
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
                intake.motorRollerOff();
            }
        }
        if (controls.deposit.value()) {
            outtake.extendToHighBasket();
            if (controls.flipBucket.value()) {
                outtake.bucketDeposit();
            } else {
                outtake.bucketToReadyForTransfer();
            }
        } else {
            outtake.returnToRetracted();
        }
        intake.updateTelemetry();
    }
}
