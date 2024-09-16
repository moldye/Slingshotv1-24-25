package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.BasicArm;
import org.firstinspires.ftc.teamcode.mechanisms.BasicSlides;

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

    private Telemetry dashboardTelemetry;
    @Override
    public void init() {
        slide = new BasicSlides(hardwareMap, configName, 0,p,i,d,f);
        arm = new BasicArm(hardwareMap, configName, 0, p,i,d,f, ticksPerDegree);
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        dashboardTelemetry.addData("target: ", target);
        if(type == 0){
            slide.moveTicks(target);
            slide.changePIDF(p,i,d,f);
            dashboardTelemetry.addData("position: ", slide.getPos());
        }else{
            arm.moveTicks(target);
            arm.changePIDF(p,i,d,f);
            dashboardTelemetry.addData("position: ", arm.getPos());
        }
        dashboardTelemetry.update();
    }
}
