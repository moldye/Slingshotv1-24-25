package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.ReLocalizer;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;

public class Robot{
    // needs to be able to access all methods for all the mechanisms used in opmodes
    public DriveTrain drivetrain;
    public ReLocalizer ultraSonics;
    public IMU imu;
    public Outtake outtake;
    public Intake intake;
    public GamepadMapping controls;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry, GamepadMapping controls) {
        imu = hardwareMap.get(IMU.class, "imu");
        // params for slingshot robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));

          // params for papaya (tester bot)
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
//                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        // will reset imu before auton only, in resetHardware

        this.controls = controls;
        drivetrain = new DriveTrain(hardwareMap, imu, telemetry, controls);
        intake = new Intake(hardwareMap, telemetry, controls);

        outtake = new Outtake(hardwareMap, 0, 0, 0, 0, 0, telemetry, controls); // tune PID values
        ultraSonics = new ReLocalizer(hardwareMap, imu);
    }

    public Pose2d reLocalize(){
        double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 90;
        double backDistance = ultraSonics.getBackDistance(currentAngle);
        double sideDistance = ultraSonics.getSideDistance(currentAngle);
        return new Pose2d(-72 + backDistance, -72 + sideDistance, Math.toRadians(currentAngle));
    }

    public void update() {
        drivetrain.update();
        //outtake.update();
        intake.update();
    }

    // This is for EVERYTHING, to be called before auton and only before auton (or in the resetHardware auton class if we don't run auton)
    public void resetHardware() {
        // reset intake
        intake.resetHardware();
        // reset dt & ultrasonics
        imu.resetYaw();
        // reset outtake
        outtake.resetHardware();
        outtake.resetEncoders();
        // reset specimen claw
    }

}