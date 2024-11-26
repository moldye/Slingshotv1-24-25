package org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure;

import static org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure.RobotMovement.followCurve;
import static org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure.RobotMovement.movement_turn;
import static org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure.RobotMovement.movement_x;
import static org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure.RobotMovement.movement_y;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

import java.util.ArrayList;

public class Follower {
    private GamepadMapping controls;
    private Robot robot;

    public Follower(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2){
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
    }

    public void follow(ArrayList<CurvePoint> allPoints, double followAngle){
        //localizer update
            //localizer.update();
        //run pathFollow
        followCurve(allPoints, followAngle);
        //set motor power
        robot.drivetrain.moveRoboCentric(movement_x,movement_y,movement_turn);
    }

    public void setStartPos(Pose2d startPos){
        //localizer.setStartPos(startPos);
    }
}
