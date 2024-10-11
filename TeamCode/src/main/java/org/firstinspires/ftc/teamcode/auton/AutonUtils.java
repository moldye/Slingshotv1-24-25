package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class AutonUtils {
    public static Pose2d calcStartPos(boolean isBlue, boolean nextToBasket){
        return new Pose2d(12,-60, Math.toRadians(270));//Todo: get values from field and based on isblue and nexttobasket, return the right value
    }
}
