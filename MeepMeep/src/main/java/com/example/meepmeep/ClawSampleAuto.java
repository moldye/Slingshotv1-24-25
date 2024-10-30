package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClawSampleAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 80, 2.5, 5, 12.7)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-12, -60, Math.toRadians(270)))
                                //Preloaded Spec
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //raise slides
//                                    outtake.moveTicks(1000);
                                })
                                .lineToSplineHeading(new Pose2d(-9,-34, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //lower slides a tiny bit
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //open claw
                                })
                                .waitSeconds(.3)
                                .setReversed(false)


                                //Sample 1
                                .UNSTABLE_addDisplacementMarkerOffset(30, () -> {
                                    //extendo
                                })
                                .splineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(90)), Math.toRadians(180)) //TODO: slow down here
                                .waitSeconds(0.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                //TODO: speed back up here
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
                                //TODO: slow down here

                                .lineToSplineHeading(new Pose2d(-48,-50, Math.toRadians(110)))

                                .waitSeconds(.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                .waitSeconds(0.5)
                                //TODO: speed back up here
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
                                //TODO: slow down here
                                .forward(10)
                                .waitSeconds(0.3)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //swing
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                                    //close
                                })
                                .waitSeconds(0.5)
                                //TODO: speed back up here

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