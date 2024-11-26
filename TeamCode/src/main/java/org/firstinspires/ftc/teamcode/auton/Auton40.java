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
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class Auton40 extends LinearOpMode {

    private GamepadMapping controls;
    private Robot robot;
    private static IntakeConstants.ActiveIntakeStates activeIntakeStates;
    private Intake intake;
    private Outtake outtake;
    private SpecimenClaw specimenClaw;
    @Override
    public void runOpMode() throws InterruptedException {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        outtake = robot.outtake;
        specimenClaw = robot.specimenClaw;
        robot.outtake.resetEncoders();
        moveLift(0);
        outtake.bucketToReadyForTransfer();
        intake.extendoFullRetract();
        intake.activeIntake.flipUp();
        specimenClaw.closeClaw();

        //robot.hardwareHardReset();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(12, -64.5, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //preloaded spec
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1300);
                })
                .lineToConstantHeading(new Vector2d(11,-31.5))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(900);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    //open claw (preloaded spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.2)

                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    moveLift(0);
                })


                //pickup #1
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    semi-extend
                    moveExtendo(0.2);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            pivot intake
                    intake.activeIntake.flipDownFull();
                    intake.activeIntake.motorRollerOnToIntake();
                })

                .splineToSplineHeading(new Pose2d(24,-36, Math.toRadians(18)), Math.toRadians(18))
                .forward(7)


                //O-zone #1
                .setReversed(true)
                .UNSTABLE_addDisplacementMarkerOffset(10, () -> {
//                                            extendo full
                    intake.extendoFullExtend();
                })
                .lineToSplineHeading(new Pose2d(20,-47, Math.toRadians(327)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            run transfer
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                // intake off
                    intake.activeIntake.motorRollerOff();
                    intake.extendoFullRetract();
                })
                .waitSeconds(0.2)



                //pickup #2
                .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                                            intake
                    intake.activeIntake.motorRollerOnToIntake();

                })

                .setReversed(false)
                .UNSTABLE_addDisplacementMarkerOffset(8, () -> {
//                                            intake
                    moveExtendo(0);
                })
                .lineToLinearHeading(new Pose2d(35,-38, Math.toRadians(33)))
                .waitSeconds(0.15)
                //O-zone #2
                .setReversed(true)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    intake.extendoFullExtend();
                })
                .lineToSplineHeading(new Pose2d(30,-46, Math.toRadians(325)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    intake.activeIntake.motorRollerOff();
                    intake.extendoFullRetract();
                })
                .waitSeconds(0.2)



                //pickup #3

                .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
                    intake.activeIntake.motorRollerOnToIntake();
                })
                .setReversed(false)
                .UNSTABLE_addDisplacementMarkerOffset(8, () -> {
//                                            intake
                    moveExtendo(0.04);
                })
                .lineToLinearHeading(new Pose2d(45,-38, Math.toRadians(30)))
                .waitSeconds(0.15)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveExtendo(0.18);
                })
                //O-zone #3
                .setReversed(true)
                .lineToSplineHeading(new Pose2d(40,-48, Math.toRadians(322)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.activeIntake.transferSample();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    intake.activeIntake.flipToTransfer();
                    intake.activeIntake.motorRollerOff();
                })
                .waitSeconds(0.2)


//                //go back to hp #1
                .lineToLinearHeading(new Pose2d(40,-56,Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.extendoFullRetract();
                })
                .back(9.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.05)
//
                //go to box #1
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1350);
                })
                .lineToSplineHeading(new Pose2d(-4,-38,Math.toRadians(270)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(900);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.12, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.1)
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    moveLift(0);
                })
                .setReversed(false)


                //go back to hp #2
                .lineToLinearHeading(new Pose2d( 40,-59,Math.toRadians(90)))
                .back(7.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.05)


                //go to box #2
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1350);
                })
                .lineToSplineHeading(new Pose2d(-2,-38,Math.toRadians(270)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(900);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.12, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.1)
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    moveLift(0);
                })
                .setReversed(false)


                //go back to hp #3
                .lineToLinearHeading(new Pose2d( 40,-59,Math.toRadians(90)))
                .back(7.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.05)

                //go to box #3
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1350);
                })
                .lineToSplineHeading(new Pose2d(0,-38,Math.toRadians(270)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(900);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.12, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                    moveLift(0);
                    intake.extendoFullExtend();
                })
                .waitSeconds(.1)

                .splineTo(new Vector2d(25,-55),Math.toRadians(-35))

                .build();
        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }

    public void moveLift(int ticks){
        robot.outtake.outtakeSlideLeft.setTargetPosition(ticks);
        robot.outtake.outtakeSlideRight.setTargetPosition(ticks);
        robot.outtake.outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideLeft.setPower(1);
        robot.outtake.outtakeSlideRight.setPower(1);
    }
    public void moveExtendo(double pos){
        robot.intake.leftExtendo.setPosition(pos);
        robot.intake.rightExtendo.setPosition(pos);
    }

}