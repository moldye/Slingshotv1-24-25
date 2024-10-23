package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class SampleAuton16 extends LinearOpMode {

    private GamepadMapping controls;
    private Robot robot;
    private static IntakeConstants.IntakeState intakeState;
    private Intake intake;
    private Outtake outtake;
    @Override
    public void runOpMode() throws InterruptedException {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        outtake = robot.outtake;
        robot.outtake.resetEncoders();
        moveLift(0);
        outtake.bucketToReadyForTransfer();
        intake.extendoFullRetract();
        intake.flipUp();

        robot.hardwareHardReset();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(-12, -63.5, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //start by raising slides to go score
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //raise slides
//                    moveLift(2400);
                })
                .setReversed(true)
                //preload to bucket
                .splineToLinearHeading(new Pose2d(-55,-59,Math.toRadians(45)),Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
//                    outtake.bucketDeposit();
                })
                .waitSeconds(0.2)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
//                    moveLift(0);
                })

                //1st yellow to bucket
                .splineToLinearHeading(new Pose2d(-50,-50,Math.toRadians(90)),Math.toRadians(45))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                   extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    intake.flipDownFull();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    deextend();
                })
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-55,-59,Math.toRadians(45)),Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    //transfer sample
//                    intake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    //raise slides
//                    moveLift(2400);
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
//                    outtake.bucketDeposit();
                })
                .waitSeconds(0.2)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
//                    moveLift(0);
                })



                //MNEXT SAMPLE
                .splineToLinearHeading(new Pose2d(-55,-50,Math.toRadians(90)),Math.toRadians(45))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    intake.flipDownFull();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    deextend();
                })

                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-54,-58,Math.toRadians(45)),Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    //transfer sample
//                    intake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    //raise slides
//                    moveLift(2400);
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
//                    outtake.bucketDeposit();
                })
                .waitSeconds(0.2)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
//                    moveLift(0);
                })


                //NEXT SAMPLE
                .splineToLinearHeading(new Pose2d(-46,-53,Math.toRadians(135)),Math.toRadians(45))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    intake.flipDownFull();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    deextend();
                })

                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-54,-58,Math.toRadians(45)),Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    //transfer sample
//                    intake.motorRollerOnToClear();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    //raise slides
//                    moveLift(2400);
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
//                    outtake.bucketDeposit();
                })
                .waitSeconds(0.2)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
//                    moveLift(0);
                })


//                //beyond this point is the other 3 samples
                .splineTo(new Vector2d(-12,-45),Math.toRadians(0))
//
                .lineToSplineHeading(new Pose2d(30,-45,Math.toRadians(30)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    intake.flipDownFull();
                })



                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //deextend();
                })
                .setReversed(true)
                .lineToSplineHeading(new Pose2d(-12,-45, Math.toRadians(0)))
                //why do we need offset here
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //offset 0.75
                    //transfer sample
//                    intake.motorRollerOnToClear();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
                })
//                .splineTo(new Vector2d(-54,-58),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //next sample
//                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
//                .lineToSplineHeading(new Pose2d(36,-36,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
//                })
//                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //next sample
//                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
//                .lineToSplineHeading(new Pose2d(40,-36,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
//                })
//                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //park
//                .setReversed(false)
//                .splineToLinearHeading(new Pose2d(-30, -12, Math.toRadians(90)),Math.toRadians(0))
                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }

        public void extendoIntake(){
        //extendo out
//        robot.intake.extendoFullExtend();
//        robot.intake.flipDownFull();
        //run intake
//        intake.motorRollerOnToIntake();
    }
    public void deextend(){
        //deeextend
//        robot.intake.flipUp();
//        robot.intake.extendoFullRetract();
        //stop Intake
//        intake.motorRollerOff();
    }
    public void moveLift(int ticks){
        robot.outtake.outtakeSlideLeft.setTargetPosition(ticks);
        robot.outtake.outtakeSlideRight.setTargetPosition(ticks);
        robot.outtake.outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideLeft.setPower(1);
        robot.outtake.outtakeSlideRight.setPower(1);
    }
}