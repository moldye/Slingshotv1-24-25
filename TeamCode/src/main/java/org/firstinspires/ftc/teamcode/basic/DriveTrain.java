package org.firstinspires.ftc.teamcode.basic;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveTrain {

    // 435 -> TODO: Number Souren wants us to remember
    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;
    private IMU imu;

    private Telemetry telemetry;
    private double newX = 0;
    private double newY = 0;

    private double targetAngle = 0;


    // public while being tuned on dashboard
    public static double turnKP;
    public static double turnKI;
    public static double turnKD;
    private PIDCoefficients turnCoeffs = new PIDCoefficients(turnKP, turnKI, turnKD);
    private PIDFController turnController = new PIDFController(turnCoeffs);

    public DriveTrain(HardwareMap hardwareMap, IMU imu, Telemetry telemetry){

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        leftBack.setDirection(DcMotorEx.Direction.FORWARD);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightBack.setDirection(DcMotorEx.Direction.REVERSE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.imu = imu;

        this.telemetry = telemetry;
    }

    // this is for testing, only used by testing methods
    public DriveTrain(DcMotorEx lF, DcMotorEx rF, DcMotorEx lB, DcMotorEx rB, IMU imu) {
        leftFront = lF;
        rightFront = rF;
        leftBack = lB;
        rightBack = rB;
        this.imu = imu;
    }


    public void moveRoboCentric(double strafe, double drive, double turn){
        // targetAngle -= turn * 10; // tune 10 depending on speed
        //if (lockHeadingMode) {
        double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        turn = lockHeading(targetAngle, currentAngle);
        //}

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        leftFront.setPower((drive + strafe + turn) / denominator);
        leftBack.setPower((drive - strafe + turn) / denominator);
        rightFront.setPower((drive - strafe - turn) / denominator);
        rightBack.setPower((drive + strafe - turn) / denominator);
    }

    // this really should be called driver centric, but whatevs

    public void moveFieldCentric(double inX, double inY, double turn, double currentAngle){
        currentAngle += 90;
        double radian = Math.toRadians(currentAngle);
        double cosTheta = Math.cos(radian);
        double sinTheta = Math.sin(radian);
        newX = (-inX * sinTheta) - (inY * cosTheta);
        newY = (-inX * cosTheta) + (inY * sinTheta);

        moveRoboCentric(newX,newY,-turn); // may need to get rid of this - on turn
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double angleWrap(double angle) {
        angle = Math.toRadians(angle);
        // Changes any angle between [-180,180] degrees
        // If rotation is greater than half a full rotation, it would be more efficient to turn the other way
        while (Math.abs(angle) > Math.PI) {
            angle -= 2 * Math.PI * (angle > 0 ? 1 : -1); // if angle > 0 * 1, < 0 * -1
        }
        return Math.toDegrees(angle);
    }

    public double lockHeading(double angleToLock, double currentHeading) {
        double error = angleWrap(angleToLock - currentHeading);
        turnController.setTargetPosition(0);
        return -turnController.update(error);
    }

    public void changePID(double inP, double inI, double inD){
        turnKP = inP; turnKI = inI; turnKD = inD;
    }
}