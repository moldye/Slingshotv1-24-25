package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


public class BlueSpeciAuton extends LinearOpMode {
    SampleMecanumDrive drive;
    boolean isBlue = true; //Todo
    boolean nextToBasket = false;//Todo

    @Override
    public void runOpMode() throws InterruptedException {
        drive.trajectorySequenceBuilder(AutonUtils.calcStartPos(isBlue, nextToBasket)).back(30)
                .waitSeconds(.5)
                .forward(5)

                //go to HP
                .setReversed(true)
                .lineToSplineHeading(new Pose2d(36, -35, Math.toRadians(180)))
                .splineTo(new Vector2d(60, -60 ), Math.toRadians(270))
                .waitSeconds(.5)

                //go to box
                .setReversed(false)
                .splineTo(new Vector2d(36, -35), Math.toRadians(180))
                .lineToSplineHeading(new Pose2d(9, -35, Math.toRadians(270)))
                .back(5)
                .waitSeconds(.5)
                .forward(5)

                //go to HP
                .setReversed(true)
                .lineToSplineHeading(new Pose2d(36, -35, Math.toRadians(180)))
                .splineTo(new Vector2d(60, -60 ), Math.toRadians(270))
                .waitSeconds(.5)

                //go to box
                .setReversed(false)
                .splineTo(new Vector2d(36, -35), Math.toRadians(180))
                .lineToSplineHeading(new Pose2d(6, -35, Math.toRadians(270)))
                .back(5)
                .waitSeconds(.5)
                .forward(5)

                //get sample
                .splineToSplineHeading(new Pose2d(25,-50,Math.toRadians(-60)),Math.toRadians(0))
                .lineToSplineHeading(new Pose2d(48, -50, Math.toRadians(90)))
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


                .build();
    }
}
