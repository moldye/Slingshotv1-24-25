package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.basic.BasicArm;
import org.firstinspires.ftc.teamcode.basic.BasicSlides;

@TeleOp
@Config
public class MechPIDTuning extends OpMode {

    private BasicSlides slide;
    private BasicArm arm;

    //TODO edit every variables value under this line b4 tuning
    //use only for arm
    private double ticksPerDegree = 0;
    //for both
    private String configName = "";
    public static int target = 0;
    public static double p = 0, i = 0, d = 0, f = 0;
    private int type = 0; // 0 is slides 1 is arm
    @Override
    public void init() {
        slide = new BasicSlides(hardwareMap, configName, p,i,d,f);
        arm = new BasicArm(hardwareMap, configName, p,i,d,f, ticksPerDegree);
    }

    @Override
    public void loop() {
        telemetry.addData("target: ", target);
        if(type == 0){
            slide.moveTicks(target);
            slide.changePIDF(p,i,d,f);
            telemetry.addData("position: ", slide.getPos());
        }else{
            arm.moveTicks(target);
            arm.changePIDF(p,i,d,f);
            telemetry.addData("position: ", arm.getPos());
        }
        telemetry.update();
    }
}
