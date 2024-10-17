package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;




import java.util.List;


import javax.imageio.ImageIO;
import java.awt.Robot;

public class Sample16BlueAuton {
    static boolean isBasket = true;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 55, 2.5, 2, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12*(isBasket?-1:1), -60, Math.toRadians(270)))
//                                .lineTo(new Vector2d(-12, -60))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                     //raise slides
                                })
                                .back(28)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //place specimen
                                    //lower  slides
                                })

                                //SAMPLE INTAKE STARTS HERE
                                .splineToLinearHeading(new Pose2d(-50,-50,Math.toRadians(90)),Math.toRadians(180))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo out
                                    //run intkw
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //deeextend
                                })
                                .turn(Math.toRadians(-45))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip outtake
                                    //lower slides
                                })



                                //MNEXT SAMPLE
                                .turn(Math.toRadians(60))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo out
                                    //run intkw
                                })

                                .waitSeconds(0.5)

                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //deeextend
                                })

                                .turn(Math.toRadians(-60))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip outtake
                                    //lower slides
                                })


                                //NEXT SAMPLE
                                .turn(Math.toRadians(90))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo out
                                    //run intkw
                                })

                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //deeextend
                                })
                                .turn(Math.toRadians(-90))
                                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                    //transfer sample
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip outtake
                                    //lower slides
                                })
                                .waitSeconds(1.5)


                                //beyond this point is the other 3 samples
                                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))

                                .lineToSplineHeading(new Pose2d(30,-36,Math.toRadians(30)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo out
                                    //run intkw
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //deeextend
                                })
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //transfer sample
                                })
                                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))

                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
                                })
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip outtake
                                    //lower slides
                                })




                                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
                                .lineToSplineHeading(new Pose2d(36,-36,Math.toRadians(30)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //extendo out
                                    //run intkw
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //deeextend
                                })
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //transfer sample
                                })
                                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))

                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //raise slides
                                })
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //flip outtake
                                    //lower slides
                                })
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