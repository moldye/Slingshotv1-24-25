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

public class ClawSample04Auto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 80, 2.5, 5, 12.7)
                .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-36, -63.5, Math.toRadians(0)))
                                //start by raising slides to go score
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //raise slides
//                                    outtake.moveTicks(2400);
                                })
//                                        .setVelConstraint()
                                .setReversed(true)
                                //preload to bucket
                                .lineToLinearHeading(new Pose2d(-50,-50,Math.toRadians(45)))
                                .back(10)
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket
//                                    outtake.bucketDeposit();
                                })
                                .waitSeconds(0.2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    outtake.returnToRetracted();
                                })

                                //Sample 1
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo
                                })
                                .lineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(90)))
                                .waitSeconds(0.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo in swing up
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //transfer (claw open)
                                })
                                .setReversed(true)
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides up
                                })
                                .waitSeconds(1)
                                .turn(Math.toRadians(-45))
                                .lineToConstantHeading(new Vector2d(-58,-58))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //outtake up
                                })
                                .waitSeconds(.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides down bucket down extendo out
                                })
                                .waitSeconds(0.5)


                                //Sample 2
                                .setReversed(false)
                                .lineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(110)))
                                .waitSeconds(.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo in swing up
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //transfer (claw open)
                                })
                                .setReversed(true)
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides up
                                })
                                .waitSeconds(1)
                                .turn(Math.toRadians(-65))
                                .lineToSplineHeading(new Pose2d(-58,-58, Math.toRadians(45)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //outtake up
                                })
                                .waitSeconds(.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides down bucket down extendo out
                                })
                                .waitSeconds(0.5)


                                //Sample 3
                                .setReversed(false)
                                .lineToSplineHeading(new Pose2d(-36,-25, Math.toRadians(180)))
                                .forward(10)
                                .waitSeconds(0.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo in swing up
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //transfer (claw open)
                                })
                                .back(10)
                                .setReversed(true)
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides up
                                })
                                .waitSeconds(.5)
                                .turn(Math.toRadians(-65))
                                .lineToSplineHeading(new Pose2d(-58,-58, Math.toRadians(45)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //outtake up
                                })
                                .waitSeconds(.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //slides down bucket down extendo out
                                })
                                .waitSeconds(0.5)
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