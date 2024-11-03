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

public class SpecimenRedAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 55, 2.5, 2, 14)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(-12, 60, Math.toRadians(450)))

                                        .back(30)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (preloaded spec)
                                        })
                                        .forward(5)


                                        //go ti HP
                                        .setReversed(true)
                                        .lineToSplineHeading(new Pose2d(-36, 35, Math.toRadians(360)))
                                        .splineTo(new Vector2d(-60, 60 ), Math.toRadians(450))
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //close claw
                                        })

                                        //go to box
                                        .setReversed(false)
                                        .splineTo(new Vector2d(-36, 35), Math.toRadians(360))
                                        .lineToSplineHeading(new Pose2d(-9, 35, Math.toRadians(450)))
                                        .back(5)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (second spec)
                                        })
                                        .forward(5)

                                        //go ti HP
                                        .setReversed(true)
                                        .lineToSplineHeading(new Pose2d(-36, 35, Math.toRadians(360)))
                                        .splineTo(new Vector2d(-60, 60 ), Math.toRadians(450))
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //close claw
                                        })

                                        //go to box
                                        .setReversed(false)
                                        .splineTo(new Vector2d(-36, 35), Math.toRadians(360))
                                        .lineToSplineHeading(new Pose2d(-6, 35, Math.toRadians(450)))
                                        .back(5)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (third spec)
                                        })
                                        .forward(5)

                                        //get sample
                                        .splineToSplineHeading(new Pose2d(-25,50,Math.toRadians(120)),Math.toRadians(180))
                                        .lineToSplineHeading(new Pose2d(-48, 50, Math.toRadians(270)))
                                        .waitSeconds(0.5)
                                        .turn(Math.toRadians(-40))
                                        .waitSeconds(0.5)
                                        .turn(Math.toRadians(40))
                                        .waitSeconds(0.5)
                                        .turn(Math.toRadians(-50))
                                        .waitSeconds(0.5)
                                        .turn(Math.toRadians(50))

                                        .back(10)



//                                .waitSeconds(0.5)
//                                .turn(Math.toRadians(50))


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



