package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.fsm.ClawCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class FSMTest extends OpMode {
    private GamepadMapping controls;
    private ClawCycle cycle;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ClawCycle(telemetry, controls, robot);

        intake = robot.intake;
        outtake = robot.outtake;

        robot.outtake.resetEncoders();
        robot.outtake.setMotorsToTeleOpMode();

        robot.intake.resetHardware();
        robot.outtake.resetHardware();
    }

//    @Override
//    public void init_loop() {
//        telemetry.addLine("If our alliance is blue, press gamepad1's x, BEFORE you start");
//        telemetry.addLine("The alliance color defaults to blue");
//        telemetry.addData("Color Sensor Is Blue", robot.intake.activeIntake.colorSensor.getIsBlue());
//        controls.isBlue.update(gamepad1.x);
//        if (controls.isBlue.value()) {
//            intake.activeIntake.colorSensor.setIsBlue(true);
//        } else {
//            intake.activeIntake.colorSensor.setIsBlue(false);
//        }
//    }

    @Override
    public void loop() {
        // already does dt & controls.update();
        cycle.clawIntakeUpdate();
        telemetry.addData("transferState", cycle.transferState.toString());
    }
}
