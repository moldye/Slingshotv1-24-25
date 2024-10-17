package org.firstinspires.ftc.teamcode.mechanisms.physicalTestsNStuff;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.opencv.core.Scalar;

import java.util.Arrays;
import java.util.List;

public class SensorColorCalibration extends OpMode {
    int iterationCount = 0;
    double[] currentMean = new double[3];
    ColorSensor sensor;
    List<Object> test;
    @Override
    public void init() {

        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    @Override
    public void loop() {
        telemetry.clearAll();
        //uh, I feel like i had to add brackets, this is pretty much unnessesary...
        if(sensor != null){
            currentMean[0] = (sensor.red()+currentMean[0]*iterationCount)/(iterationCount+1);
            currentMean[1] = (sensor.green()+currentMean[0]*iterationCount)/(iterationCount+1);;
            currentMean[2] = (sensor.blue()+currentMean[0]*iterationCount)/(iterationCount+1);;
            iterationCount++;
        }
        //You can reset the current set using the options key!
        if(gamepad1.options){
            currentMean = new double[3];
            iterationCount = 0;
        }
        telemetry.addData("mean:", trimDoubleArray(currentMean));
    }
    public int[] trimDoubleArray(double[] array){
        //I chose to do this to sacrifice a little bit of memory for a little bit of speed :)
        int len = array.length;
        int[] a = new int[len];
        for(int i = 0;i<len;i++){
            a[i] = (int) array[i];
        }
        return a;
    }
}
