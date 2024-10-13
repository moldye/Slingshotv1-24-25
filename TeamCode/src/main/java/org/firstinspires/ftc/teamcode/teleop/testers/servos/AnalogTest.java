package org.firstinspires.ftc.teamcode.teleop.testers.servos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp
public class AnalogTest extends OpMode {
    private GamepadMapping controls;
    private Intake intake;
    public static double servoPos = 0;
    private Telemetry dashboardTelemetry;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        intake = new Intake(hardwareMap, telemetry, controls);
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        double encoderPos = intake.calculateFlipWithAnalog();

        dashboardTelemetry.addLine("Analog Pivot Servo Test");
        dashboardTelemetry.addData("encoderPos: ", encoderPos);
        dashboardTelemetry.addData("getPos:", intake.pivotAxon.getPosition());

        intake.pivotAxon.setPosition(servoPos);
    }
}
