package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.io.File;
import java.io.IOException;

public class Sample16BlueAuton {
    private HardwareMap hardwareMap;
    private SampleMecanumDrive drive;
    private boolean isBlue;
    private boolean nearBasket;

    public void run() {
        // TODO: Don't forget to call reset Hardware here
        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        drive.trajectorySequenceBuilder(new Pose2d(-12, -60, Math.toRadians(270)))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //raise slides
            })
            .back(28)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //place specimen
                //lower  slides
            })
            //SAMPLE INTAKE STARTS HERE
            .splineToLinearHeading(new Pose2d(-50, -50, Math.toRadians(90)), Math.toRadians(180))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //extendo out
                //run intkw
            })
            .waitSeconds(0.5)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //deeextend
            })
            .turn(Math.toRadians(-45))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
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
            .UNSTABLE_addTemporalMarkerOffset(0., () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
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
            .UNSTABLE_addTemporalMarkerOffset(0., () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                //raise slides
            })
            .waitSeconds(0.5)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //flip outtake
                //lower slides
            })


            //beyond this point is the other 3 samples
            .splineTo(new Vector2d(-12, -36), Math.toRadians(0))
            .lineToSplineHeading(new Pose2d(30, -36, Math.toRadians(30)))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //extendo out
                //run intkw
            })
            .waitSeconds(0.5)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //deeextend
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-12, -36, Math.toRadians(0)))
            .splineTo(new Vector2d(-50, -50), Math.toRadians(225))
            .UNSTABLE_addTemporalMarkerOffset(0., () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                //raise slides
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //flip outtake
                //lower slides
            })


            .splineTo(new Vector2d(-12, -36), Math.toRadians(0))
            .lineToSplineHeading(new Pose2d(36, -36, Math.toRadians(30)))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //extendo out
                //run intkw
            })
            .waitSeconds(0.5)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //deeextend
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-12, -36, Math.toRadians(0)))
            .splineTo(new Vector2d(-50, -50), Math.toRadians(225))
            .UNSTABLE_addTemporalMarkerOffset(0., () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                //raise slides
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //flip outtake
                //lower slides
            })


            .splineTo(new Vector2d(-12, -36), Math.toRadians(0))
            .lineToSplineHeading(new Pose2d(40, -36, Math.toRadians(30)))
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //extendo out
                //run intkw
            })
            .waitSeconds(0.5)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //deeextend
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-12, -36, Math.toRadians(0)))
            .splineTo(new Vector2d(-50, -50), Math.toRadians(225))
            .UNSTABLE_addTemporalMarkerOffset(0., () -> {
                //transfer sample
            })
            .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                //raise slides
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                //flip outtake
                //lower slides
            })
            .build();

    }


    public Sample16BlueAuton(HardwareMap map, boolean isBlue, boolean nearBasket){
        hardwareMap = map;
        drive = new SampleMecanumDrive(hardwareMap);
        this.isBlue = isBlue;
        this.nearBasket = nearBasket;
    }
}

