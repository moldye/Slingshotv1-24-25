package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.Cycle;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
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
//        intake.extendoFullRetract();
//        intake.flipUp();
        outtake.resetEncoders();
        outtake.returnToRetracted();
    }

    @Override
    public void loop() {
        controls.update();
        if (controls.intakeOnToIntake.locked()) {
            intake.motorRollerOnToIntake();
            if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.BLUE) && !intake.colorSensor.isBlue) {
                intake.pushOutSample();
            } else if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.RED) && intake.colorSensor.isBlue) {
                intake.pushOutSample();
            } else if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.BLUE) && intake.colorSensor.isBlue
                    || intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.RED) && !intake.colorSensor.isBlue) {
                telemetry.addData("Sample: ", intake.colorSensor.checkSample());
            }
        } else {
            intake.motorRollerOff();
        }
        telemetry.addData("Is Blue: ", controls.isBlue.value());
    }
}
