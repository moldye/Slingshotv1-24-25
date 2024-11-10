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


public class Sample16Auton {

    public static void main (String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 55, 2.5, 2, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-12, -63.5, Math.toRadians(0)))
                        //start by raising slides to go score
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //raise slides

                        })
                        .setReversed(true)
                        //preload to bucket
                        .splineToLinearHeading(new Pose2d(-56,-58,Math.toRadians(45)),Math.toRadians(225))
                        .setReversed(false)
                        .back(3)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //flip bucket

                        })
                        .waitSeconds(0.75)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // reset slides
                        })

                        //1st yellow to bucket
                        .lineToConstantHeading(new Vector2d(-58, -56))
                        .turn(Math.toRadians(28))
                        .turn(Math.toRadians(27))
            //                .splineToLinearHeading(new Pose2d(-50,-59,Math.toRadians(90)),Math.toRadians(45))

                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // extend
                        })
                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                            // flip down pivot
                        })
                        .waitSeconds(0.75)
                        .forward(10)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // retract
                        })
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(-54,-58,Math.toRadians(45)),Math.toRadians(225))

                        .setReversed(false)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //transfer sample

                        })
                        .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                            // transfer off
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
                            // reset slides + bucket
                        })



                            //NEXT SAMPLE - second yellow
//                .splineToLinearHeading(new Pose2d(-55,-60,Math.toRadians(90)),Math.toRadians(45))
                            .lineToConstantHeading(new Vector2d(-58, -56))
                            .turn(Math.toRadians(45))
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                // extend
                            })
                            .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                                // flip down pivot
                            })
                            .waitSeconds(0.9)
                            .forward(10.5)
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                // retract
                            })

                            .setReversed(true)
                            .splineToLinearHeading(new Pose2d(-52,-56.5,Math.toRadians(45)),Math.toRadians(225))
                            .setReversed(false)
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                //transfer sample

                            })
                            .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                // transfer off
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
                                // reset slides + bucket
                            })

                            //NEXT SAMPLE - third yellow
                            .splineToLinearHeading(new Pose2d(-36,-29,Math.toRadians(180)),Math.toRadians(90))
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                // extend
                            })
                            .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                                // pivot down
                            })
                            // TODO try commenting this out to change the last sample intake
                            .waitSeconds(1)
                            .forward(9)
                            .back(10)
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                // retract
                            })
                            .setReversed(true)
                            .splineToLinearHeading(new Pose2d(-51,-56,Math.toRadians(45)),Math.toRadians(225))
                            .setReversed(false)
                            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                //transfer sample

                            })
                            .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                                // transfer off
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
                                // reset slides + bucket
                            })


//                //beyond this point is the other 3 samples
//                .splineTo(new Vector2d(-12,-45),Math.toRadians(0))
////
//                .lineToSplineHeading(new Pose2d(30,-45,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.flipDownFull();
//                })
//
//
//
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-45, Math.toRadians(0)))
//
                        //TODO: TUNED TO HERE, TO REST IF POSSIBLE
                        //why do we need offset here
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        //offset 0.75
                        //transfer sample
//                    intake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
//                })
//                .splineTo(new Vector2d(-54,-58),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //next sample
//                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
//                .lineToSplineHeading(new Pose2d(36,-36,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
//                })
//                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //next sample
//                .splineTo(new Vector2d(-12,-36),Math.toRadians(0))
//                .lineToSplineHeading(new Pose2d(40,-36,Math.toRadians(30)))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    extendoIntake();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.flipDownFull();
//                })
//                .waitSeconds(0.5)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    deextend();
//                })
//                .setReversed(true)
//                .lineToSplineHeading(new Pose2d(-12,-36, Math.toRadians(0)))
//                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
//                    //transfer sample
//                    intake.motorRollerOnToClear();
//                })
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    intake.motorRollerOff();
//                })
//                .splineTo(new Vector2d(-50,-50),Math.toRadians(225))
//
//                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                    //raise slides
////                    moveLift(2400);
//                })
//                .waitSeconds(0.5)
//                .setReversed(false)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    //flip bucket
//                    outtake.bucketDeposit();
//                })
//                .waitSeconds(0.2)
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                    moveLift(0);
//                })
//
//                //park
                    .setReversed(false)
                    .splineToLinearHeading(new Pose2d(-48, -36, Math.toRadians(90)),Math.toRadians(90))
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







