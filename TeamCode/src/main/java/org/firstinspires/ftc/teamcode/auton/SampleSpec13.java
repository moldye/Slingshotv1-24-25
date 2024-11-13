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
public class SampleSpec13 extends LinearOpMode {

    private GamepadMapping controls;
    private Robot robot;
    private static IntakeConstants.ActiveIntakeStates activeIntakeStates;
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
        intake.activeIntake.flipUp();

        //robot.hardwareHardReset();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(-12, -63.5, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //start by raising slides to go score
                //Preloaded Spec
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //raise slides
                    moveLift(1500);
                })
                .lineToSplineHeading(new Pose2d(-9, -37, Math.toRadians(270)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //lower slides a tiny bit
                    moveLift(0);
                })
                .waitSeconds(.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //open claw
                    robot.specimenClaw.openClaw();
                })
                .waitSeconds(.3)
                .setReversed(false)


                //1st yellow to bucket
                .lineToConstantHeading(new Vector2d(-58.5, -56.5))
                .turn(Math.toRadians(170))
//                .splineToLinearHeading(new Pose2d(-50,-59,Math.toRadians(90)),Math.toRadians(45))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    intake.activeIntake.flipDownFull();
//                    intake.motorRollerOnToIntake();
                })
                //.waitSeconds(0.75)
                .forward(10)
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    deextend();
                })
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-52, -56, Math.toRadians(45)), Math.toRadians(225))

                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(.2, () -> {
                    //transfer sample
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(.6, () -> {
                    intake.activeIntake.transferOff();
                    intake.activeIntake.flipUp();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //raise slides
                    moveLift(2400);
                })
                .waitSeconds(1)
                .back(4.75)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
                    outtake.bucketDeposit();
                })
                .waitSeconds(0.75)
                // TODO TEST THIS, so spec claw don't hit
                .forward(1.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
                    moveLift(0);
                })


                //MNEXT SAMPLE - second yellow
//                .splineToLinearHeading(new Pose2d(-55,-60,Math.toRadians(90)),Math.toRadians(45))
                .lineToConstantHeading(new Vector2d(-58, -56))
                .turn(Math.toRadians(55))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    intake.activeIntake.flipDownFull();
                })
                .waitSeconds(0.9)
                .forward(10.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    deextend();
                })

                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-52, -56.5, Math.toRadians(45)), Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    //transfer sample
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    intake.activeIntake.transferOff();
                    intake.activeIntake.flipUp();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //raise slides
                    moveLift(2400);
                })
                .waitSeconds(1)
                .back(5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
                    outtake.bucketDeposit();
                })
                .waitSeconds(0.75)
                // TODO TEST THIS, so spec claw don't hit
                .forward(1.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
                    moveLift(0);
                })


                //NEXT SAMPLE - third yellow
                //.splineToLinearHeading(new Pose2d(-36, -27, Math.toRadians(180)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-47, -51, Math.toRadians(131)), Math.toRadians(225))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    extendoIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    intake.activeIntake.flipDownFull();
                })
                // TODO try commenting this out to change the last sample intake
                .waitSeconds(1)
                .forward(9)
                .back(10)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    deextend();
                })


                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-51, -55, Math.toRadians(45)), Math.toRadians(225))
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    //transfer sample
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    intake.activeIntake.transferOff();
                    // TODO don't think this is necessary
                    intake.activeIntake.flipUp();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    //raise slides
                    moveLift(2400);
                })
                .waitSeconds(1)
                .back(5)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
                    outtake.bucketDeposit();
                })
                .waitSeconds(0.75)
                // TODO TEST THIS, so spec claw don't hit
                .forward(1.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    outtake.bucketToReadyForTransfer();
                    moveLift(0);
                })


//                //beyond this point is the other 3 samples
//                .splineTo(new Vector2d(-12,-45),Math.toRadians(0))
////
//                .lineToSplineHeading(new Pose2d(30,-45,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.activeIntake.flipDownFull();
//                })
//
//
//
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-45, Math.toRadians(0)))
//
                //TODO: TUNED TO HERE, TO REST IF POSSIBLE
                //why do we need offset here
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //offset 0.75
                //transfer sample
//                    intake.activeIntake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.activeIntake.motorRollerOff();
//                })
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
//                    intake.activeIntake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.activeIntake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.activeIntake.motorRollerOff();
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
//                    intake.activeIntake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.activeIntake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.activeIntake.motorRollerOff();
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
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-48, -36, Math.toRadians(90)), Math.toRadians(90))
                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }

    public void extendoIntake() {
        //extendo out
        robot.intake.extendoFullExtend();
        //run intake
        intake.activeIntake.motorRollerOnToIntake();
    }

    public void deextend() {
        //deeextend
        robot.intake.activeIntake.pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.TRANSFER.pivotPos());
        robot.intake.extendoFullRetract();
        //stop Intake
        intake.activeIntake.motorRollerOff();
    }

    public void moveLift(int ticks) {
        robot.outtake.outtakeSlideLeft.setTargetPosition(ticks);
        robot.outtake.outtakeSlideRight.setTargetPosition(ticks);
        robot.outtake.outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideLeft.setPower(1);
        robot.outtake.outtakeSlideRight.setPower(1);
    }
}
