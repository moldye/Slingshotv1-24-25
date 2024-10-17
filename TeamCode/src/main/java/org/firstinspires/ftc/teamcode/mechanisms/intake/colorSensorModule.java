package org.firstinspires.ftc.teamcode.mechanisms.intake;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants.SampleTypes;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class colorSensorModule {
    Telemetry telemetry;
    ColorSensor sensor;
    boolean isBlue;
    //TODO:I'm too lazy to actually put this into intake, so I'm making a class for it HOORAY!!
    public colorSensorModule(Telemetry t, HardwareMap hm, boolean isBlue){
        this.telemetry = t;
        this.sensor = hm.get(ColorSensor.class, "colorSensor");
        this.isBlue = isBlue;
    }
    public SampleTypes checkSample(){
        double[] sensorVals = new double[3];
        sensorVals[0] = sensor.red();
        sensorVals[1] = sensor.green();
        sensorVals[2] = sensor.blue();
        SampleTypes best = null;
        double least = 1000000000;
        for(SampleTypes s : SampleTypes.values()){
            if(Math.abs(sensorVals[0]-s.color[0])+Math.abs(sensorVals[1]-s.color[1])+Math.abs(sensorVals[2]-s.color[2]) < least){
                least = Math.abs(sensorVals[0]-s.color[0])+Math.abs(sensorVals[1]-s.color[1])+Math.abs(sensorVals[2]-s.color[2]);
                best = s;
            };
        }
        return best;
    }
    public boolean opposingColor(){
        double[] sensorVals = new double[3];
        sensorVals[0] = sensor.red();
        sensorVals[1] = sensor.green();
        sensorVals[2] = sensor.blue();
        SampleTypes best = null;
        double least = 1000000000;
        for(SampleTypes s : SampleTypes.values()){
            if(Math.abs(sensorVals[0]-s.color[0])+Math.abs(sensorVals[1]-s.color[1])+Math.abs(sensorVals[2]-s.color[2]) < least){
                least = Math.abs(sensorVals[0]-s.color[0])+Math.abs(sensorVals[1]-s.color[1])+Math.abs(sensorVals[2]-s.color[2]);
                best = s;
            };
        }
        //if we are blue alliance, return the sample color == red, else return color = blue!
        return isBlue?(best.equals(SampleTypes.RED)):(best.equals(SampleTypes.BLUE));
    }
}
