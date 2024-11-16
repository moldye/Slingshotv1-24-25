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

//@Autonomous
public class ClawSampleAuton04 extends LinearOpMode {

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


        Pose2d startPose = new Pose2d(-36, -63.5, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)

                //start by raising slides to go score
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //raise slides
                    moveLift(2400);
                })

                .setReversed(true)
                //preload to bucket
                .lineToLinearHeading(new Pose2d(-50,-50,Math.toRadians(45)))
                .back(10)
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //flip bucket
                    outtake.bucketDeposit();
                })
                .waitSeconds(0.2)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                   //slides down bucket down extendo out
                   prepareForNextSample();
                })

                //Sample 1
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //extendo
                    robot.intake.extendoFullExtend();
                })
                .lineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //swing
                    robot.intake.claw.moveToPickingSample();



                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    //close
                    robot.intake.claw.closeClaw();

                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //extendo in swing up
                    robot.intake.extendoFullRetract();
                    robot.intake.claw.resetClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //transfer (claw open)
                    robot.intake.claw.moveToTransfer();
                    robot.intake.claw.openClaw();


                })
                .setReversed(true)
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //move pivot a little to not interfere w bucket
                    robot.intake.claw.moveToOuttaking();
                    //slides up
                    moveLift(2400);

                })
                .waitSeconds(1)
                .turn(Math.toRadians(-45))
                .lineToConstantHeading(new Vector2d(-58,-58))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //outtake up
                    outtake.bucketDeposit();

                })
                .waitSeconds(.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //slides down bucket down extendo out
                    prepareForNextSample();
                })
                .waitSeconds(0.5)


                //Sample 2
                .setReversed(false)
                .lineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(110)))
                .waitSeconds(.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //swing
                    robot.intake.claw.moveToPickingSample();

                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    //close
                    robot.intake.claw.closeClaw();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //extendo in swing up
                    robot.intake.extendoFullRetract();
                    robot.intake.claw.resetClaw();

                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //transfer (claw open)
                    robot.intake.claw.moveToTransfer();
                    robot.intake.claw.openClaw();

                })
                .setReversed(true)
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //change pivot so no interfere w bucket
                    robot.intake.claw.moveToOuttaking();
                    // slides up
                    moveLift(2400);


                })
                .waitSeconds(1)
                .turn(Math.toRadians(-65))
                .lineToSplineHeading(new Pose2d(-58,-58, Math.toRadians(45)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //outtake up
                    outtake.bucketDeposit();
                })
                .waitSeconds(.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //slides down bucket down extendo out
                    prepareForNextSample();
                })
                .waitSeconds(0.5)


                //Sample 3
                .setReversed(false)
                .lineToSplineHeading(new Pose2d(-36,-25, Math.toRadians(180)))
                .forward(10)
                .waitSeconds(0.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //swing
                    robot.intake.claw.moveToPickingSample();

                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    //close
                    robot.intake.claw.closeClaw();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //extendo in swing up
                    robot.intake.extendoFullRetract();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    //transfer (claw open)
                    robot.intake.claw.moveToTransfer();
                    robot.intake.claw.openClaw();
                })
                .back(10)
                .setReversed(true)
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //change pivot so not interefere w bucket
                    robot.intake.claw.moveToOuttaking();
                    // slides up
                    moveLift(2400);
                })
                .waitSeconds(.5)
                .turn(Math.toRadians(-65))
                .lineToSplineHeading(new Pose2d(-58,-58, Math.toRadians(45)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //outtake up
                    robot.outtake.bucketDeposit();
                })
                .waitSeconds(.3)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //slides hanging position bucket down
                    prepareForNextSample();
                    moveLift(300);
                    //TODO: change tick value for slides
                })

//                //park
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-25, -10, Math.toRadians(180)),Math.toRadians(0))
                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }


    public void deextend(){
        //deeextend
        robot.intake.activeIntake.pivotAxon.setPosition(IntakeConstants.ActiveIntakeStates.TRANSFER.pivotPos());
        robot.intake.extendoFullRetract();
        //stop Intake
        intake.activeIntake.motorRollerOff();
    }
    public void moveLift(int ticks){
        robot.outtake.outtakeSlideLeft.setTargetPosition(ticks);
        robot.outtake.outtakeSlideRight.setTargetPosition(ticks);
        robot.outtake.outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideLeft.setPower(1);
        robot.outtake.outtakeSlideRight.setPower(1);
    }

    public void prepareForNextSample(){
        moveLift(0);
        robot.intake.extendoFullExtend();
        robot.outtake.bucketToReadyForTransfer();
    }

}