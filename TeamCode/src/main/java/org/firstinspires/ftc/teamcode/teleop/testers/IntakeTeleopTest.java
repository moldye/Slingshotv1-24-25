package org.firstinspires.ftc.teamcode.teleop.testers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    private ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    private DriveTrain dt;

    // TUNING VARS
    public static final double rExLinkagePos = 1; // extend
    public static final double lExLinkagePos = 0; // extend

    public static final double rRLinkagePos = 0; // retract
    public static final double lRLinkagePos = 1; // retract
    public static final double motorPower = 0.1;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        dt = robot.drivetrain;
    }

    @Override
    public void loop() {
        //
        //
        controls.pivot.update(gamepad1.a);
        controls.switchExtendo.update(gamepad1.b);
        controls.powerIntake.update(gamepad1.x);


        if(controls.switchExtendo.value()) {
            // intake.intakeState.setExtendLinkagePositions(rExLinkagePos, lExLinkagePos);
            intake.extendoExtend();
            intake.motorRollerOn();
            if (controls.pivot.value()) {
                intake.flipDown();
            } else {
                intake.flipUp();
            }
            if (controls.powerIntake.value()) {
                intake.motorRollerOff();
            } else {
                intake.motorRollerOn();
            }
        } else {
            intake.flipUp();
            // intake.intakeState.setRetractLinkagePositions(rRLinkagePos, lRLinkagePos);
            intake.extendoRetract();
            intake.motorRollerOff();
        }

        intake.updateTelemetry();

        controls.joystickUpdate();
        dt.update();
    }
}
