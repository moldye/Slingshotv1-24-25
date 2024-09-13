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

public class SampleBlueAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 60, 6, 6, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-60, 12, Math.toRadians(180)))
                                .back(28)
                                .splineToLinearHeading(new Pose2d(-50,50,Math.toRadians(0)),Math.toRadians(90))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-45))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(60))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-60))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(90))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-90))
                                .waitSeconds(0.5)

                                //beyond this point is the other 3 samples
                                .splineTo(new Vector2d(-36,12),Math.toRadians(270))
                                .lineToSplineHeading(new Pose2d(-36,-30,Math.toRadians(-60)))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36,12, Math.toRadians(270)))
                                .splineTo(new Vector2d(-50,50),Math.toRadians(135))
                                .waitSeconds(0.5)
                                .setReversed(false)

                                .splineTo(new Vector2d(-36,12),Math.toRadians(270))
                                .lineToSplineHeading(new Pose2d(-36,-36,Math.toRadians(-60)))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36,12, Math.toRadians(270)))
                                .splineTo(new Vector2d(-50,50),Math.toRadians(135))
                                .waitSeconds(0.5)
                                .setReversed(false)

                                .splineTo(new Vector2d(-36,12),Math.toRadians(270))
                                .lineToSplineHeading(new Pose2d(-36,-40,Math.toRadians(-60)))
                                .waitSeconds(0.5)
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36,12, Math.toRadians(270)))
                                .splineTo(new Vector2d(-50,50),Math.toRadians(135))
                                .waitSeconds(0.5)
                                .setReversed(false)
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