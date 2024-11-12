package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SampleSpec13 {
    public static void main (String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(65, 80, 2.5, 5, 12.7)
                .followTrajectorySequence(drive -> (
                        drive.trajectorySequenceBuilder(new Pose2d(-12, -63.5, Math.toRadians(0)))
                                //start by raising slides to go score
                                //Preloaded Spec
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //raise slides
                                })
                                .lineToSplineHeading(new Pose2d(-9, -37, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //lower slides a tiny bit
                                })
                                .waitSeconds(.2)
                                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                                    //open claw
                                })
                                .waitSeconds(.3)
                                .setReversed(false)


                                //1st yellow to bucket
                                .lineToConstantHeading(new Vector2d(-58.5, -56.5))
                                .turn(Math.toRadians(170))
//                .splineToLinearHeading(new Pose2d(-50,-59,Math.toRadians(90)),Math.toRadians(45))

                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOnToIntake();
                                })
                                .waitSeconds(0.75)
                                .forward(10)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-52, -56, Math.toRadians(45)), Math.toRadians(225))

                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(.2, () -> {
                                    //transfer sample

                                })
                                .UNSTABLE_addTemporalMarkerOffset(.6, () -> {

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //raise slides

                                })
                                .waitSeconds(1)
                                .back(4.75)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket

                                })
                                .waitSeconds(0.75)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })


                                //MNEXT SAMPLE - second yellow
//                .splineToLinearHeading(new Pose2d(-55,-60,Math.toRadians(90)),Math.toRadians(45))
                                .lineToConstantHeading(new Vector2d(-58, -56))
                                .turn(Math.toRadians(60))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {

                                })
                                .waitSeconds(0.9)
                                .forward(10.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })

                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-52, -56.5, Math.toRadians(45)), Math.toRadians(225))
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                                    //transfer sample

                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //raise slides

                                })
                                .waitSeconds(1)
                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket

                                })
                                .waitSeconds(0.75)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })


                                //NEXT SAMPLE - third yellow
                                //.splineToLinearHeading(new Pose2d(-36, -27, Math.toRadians(180)), Math.toRadians(90))
                                .splineToLinearHeading(new Pose2d(-47, -51, Math.toRadians(131)), Math.toRadians(225))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {

                                })
                                // TODO try commenting this out to change the last sample intake
                                .waitSeconds(1)
                                .forward(9)
                                .back(10)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })


                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-51, -55, Math.toRadians(45)), Math.toRadians(225))
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                                    //transfer sample

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {

                                    // TODO don't think this is necessary

                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //raise slides

                                })
                                .waitSeconds(1)
                                .back(5)

                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip bucket

                                })
                                .waitSeconds(0.75)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .build()
                ));
        BufferedImage img = null;
        try { img = ImageIO.read(new File("MeepMeep/intothedeep.png")); }
        catch (
                IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
