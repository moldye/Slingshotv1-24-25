package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class BucketServoTest extends OpMode {
    private GamepadMapping controls;
    private Outtake outtake;
    private double servoPos;
    private double speed = .001;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        outtake = new Outtake(hardwareMap, 0, 0, 0, 0, 0, telemetry, controls);
        outtake.bucketServo.setPosition(0);
    }

    @Override
    public void loop() {
        servoPos += gamepad1.left_stick_y * speed;
        servoPos = Math.min(Math.max(servoPos, 0.0), 1.0);

        outtake.bucketServo.setPosition(servoPos);


        telemetry.addLine("Bucket Servo Test");
        telemetry.addLine("Directions: Move left stick up/down to move stack servos");
        telemetry.addData("servoLeft: ", servoPos);
        telemetry.addData("y: ", -gamepad1.left_stick_y);
    }

    // deposit: 0
    // tilt: .5477610423136522
    // transfer ready: .7231126346979301
}
