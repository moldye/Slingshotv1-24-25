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
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 55, 2.5, 2, 14)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(-9, 60, Math.toRadians(450)))
//                                      score preloaded spec
                                        .back(30)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //tiny slides raise
//                                            extendtoRemoveSpecFromWall();
                                        })
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (preloaded spec)
                                        })
                                        .forward(9)


                                        //go ti HP
                                        .setReversed(true)
                                        .lineToConstantHeading(new Vector2d(-19, 38))
                                        .splineToConstantHeading(new Vector2d(-42, 15), Math.toRadians(90))

                                        //ready to push sample1
                                        .forward(45)


                                        //push sample 2
                                        .setReversed(true)
                                        .lineToLinearHeading(new Pose2d(-40, 25, Math.toRadians(270)))
                                        .lineToConstantHeading(new Vector2d(-40, 17))
                                        .splineToConstantHeading(new Vector2d(-52, 10), Math.toRadians(0))

                                        .back(55)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //close claw (intake spec 2)
                                        })
                                        .forward(5)
                                        //go to box
                                        .setReversed(false)
                                        .splineTo(new Vector2d(-15, 37), Math.toRadians(360))
                                        .lineToSplineHeading(new Pose2d(-4, 37, Math.toRadians(450)))
                                        .back(5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            // tiny slides raise
                                        })
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (spec + 1)
                                        })
                                        .forward(8)

                                        //go to hp (3rd spec)
                                        .lineToSplineHeading(new Pose2d(-20, 40, Math.toRadians(360)))
                                        .splineTo(new Vector2d(-39, 60 ), Math.toRadians(450))
                                        .back(4)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //close claw (spec + 2)
                                        })

                                        //back to box
                                        .splineTo(new Vector2d(-15, 37), Math.toRadians(360))
                                        .lineToSplineHeading(new Pose2d(-2, 37, Math.toRadians(450)))
                                        .back(5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //tiny slides raise
                                        })
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (spec + 2)
                                        })
                                        .forward(5)



                                        //go to HP
                                        /*.setReversed(true)
                                        .lineToSplineHeading(new Pose2d(-22, 38, Math.toRadians(353)))
                                        .splineTo(new Vector2d(-49, 60 ), Math.toRadians(450))
                                        .back(2)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //close claw (intake 3rd spec)
                                        })

                                        //go to box
                                        .setReversed(false)
                                        .splineTo(new Vector2d(-15, 37), Math.toRadians(360))
                                        .lineToSplineHeading(new Pose2d(-4, 37, Math.toRadians(450)))
                                        .back(5)
                                        .waitSeconds(.5)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                            //open claw (score third spec)
                                        })
                                        .forward(5)






//                                .waitSeconds(0.5)
//                                .turn(Math.toRadians(50))*/


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



