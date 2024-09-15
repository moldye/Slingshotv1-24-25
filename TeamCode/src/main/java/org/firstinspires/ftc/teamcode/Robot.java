package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.ReLocalizer;

public class Robot{
    // needs to be able to access all methods for all the mechanisms used in opmodes
    public DriveTrain drivetrain;
    public ReLocalizer ultraSonics;
    public IMU imu;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = hardwareMap.get(IMU.class, "imu");
        // params for slingshot robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        // params for papaya (tester bot)
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
//                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();

        drivetrain = new DriveTrain(hardwareMap, imu, telemetry);
        ultraSonics = new ReLocalizer(hardwareMap, imu);
    }

    public Pose2d reLocalize(){
        double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 90;
        double backDistance = ultraSonics.getBackDistance(currentAngle);
        double sideDistance = ultraSonics.getSideDistance(currentAngle);
        return new Pose2d(-72 + backDistance, -72 + sideDistance, Math.toRadians(currentAngle));
    }


}