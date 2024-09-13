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

public class Tester {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-60, -12, Math.toRadians(180)))

                                //goes to box
                                //score specimen (preloaded)
                                .setReversed(true)
                                .back(20)
                                .waitSeconds(.5)
                                .setReversed(false)

                                //get specimen from HP
                                .splineToLinearHeading(new Pose2d(-55,-50, Math.toRadians(0)), Math.toRadians(255))

                                .waitSeconds(0.5)
                                .setReversed(false)
                                //put specimen on box (farther down)
                                .splineToLinearHeading(new Pose2d(-40, 5, Math.toRadians(180)),Math.toRadians(90))


                                //get specimen from HP
                                .splineToLinearHeading(new Pose2d(-55,-50, Math.toRadians(0)), Math.toRadians(255))
                                .waitSeconds(0.5)
                                .setReversed(false)

                                //put specimen on box (farther forward)
                                .splineToLinearHeading(new Pose2d(-40, 0, Math.toRadians(180)),Math.toRadians(90))




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