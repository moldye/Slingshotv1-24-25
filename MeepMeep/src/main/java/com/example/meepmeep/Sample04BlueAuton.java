package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

public class Sample04BlueAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 80, 2.5, 5, 12.7)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-12, -60, Math.toRadians(0)))
                                //start by raising slides to go score
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .setReversed(true)
                                //preload to bucket
                                .splineToLinearHeading(new Pose2d(-50,-50,Math.toRadians(45)),Math.toRadians(180))
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })

                                //1st yellow to bucket
                                .turn(Math.toRadians(45))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    extendoIntake();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    deextend();
                                })
                                .turn(Math.toRadians(-45))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
//                                    intake.motorRollerOnToClear();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    intake.motorRollerOff();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })



                                //MNEXT SAMPLE
                                .turn(Math.toRadians(60))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    extendoIntake();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })

                                .turn(Math.toRadians(-60))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
//                                    intake.motorRollerOnToClear();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    intake.motorRollerOff();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })


                                //NEXT SAMPLE
                                .turn(Math.toRadians(90))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    extendoIntake();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    deextend();
                                })

                                .turn(Math.toRadians(-90))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
//                                    intake.motorRollerOnToClear();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    intake.motorRollerOff();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })


                                //beyond this point is the other 3 samples
                                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))

                                .lineToSplineHeading(new Pose2d(30,-36,Math.toRadians(30)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    extendoIntake();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    deextend();
                                })
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
//                                    intake.motorRollerOnToClear();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    intake.motorRollerOff();
                                })
                                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))

                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })

                                //next sample
                                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
                                .lineToSplineHeading(new Pose2d(36,-36,Math.toRadians(30)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    extendoIntake();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    deextend();
                                })
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
//                                    intake.motorRollerOnToClear();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    intake.motorRollerOff();
                                })
                                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))

                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })

                                //park
                                .lineToLinearHeading(new Pose2d(-30, -12, Math.toRadians(90)))



//                                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
//                                .lineToSplineHeading(new Pose2d(40,-36,Math.toRadians(30)))
//                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    //extendo out
//                                    //run intkw
//                                })
//                                .waitSeconds(0.5)
//                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    //deeextend
//                                })
//                                .setReversed(true)
//                                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
//                                    //transfer sample
//                                })
//                                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))
//
//                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                    //raise slides
//                                })
//                                .waitSeconds(0.5)
//                                .setReversed(false)
//                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    //flip outtake
//                                    //lower slides
//                                })
                                .build()
                );



        BufferedImage img = null;
        try { img = ImageIO.read(new File("MeepMeep/intothedeep.png")); }
        catch (IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}