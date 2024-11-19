package org.firstinspires.ftc.teamcode.vision;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.HoughLines;
import static org.opencv.imgproc.Imgproc.HoughLinesP;
import static org.opencv.imgproc.Imgproc.MORPH_CLOSE;
import static org.opencv.imgproc.Imgproc.MORPH_CROSS;
import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.MORPH_GRADIENT;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.approxPolyDP;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

import android.graphics.Canvas;
import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class birdsEyeSampleSearchYellow implements VisionProcessor {
    //OpenCV sim stuff
    public boolean isBlue;
    public boolean seeYellowFrame = false;
    public boolean seePostThreshold = false;
    public boolean seeMorphology = false;
    public boolean seeCanny = false;
    public boolean seeHough = false;

    public Telemetry telemetry = null;
    //matrices, in order of appearance
    Mat yellowBasedFrame = new Mat();
    Mat groundSubmat = new Mat();

    //definitions/boundaries of matrices
    public double thresholdOffset = 50;

    Size frameSize = new Size();

    Point groundTopLeft = new Point(1,1);
    Point groundBottomRight = new Point(40,40);

    //Matrix Data
    double avg = 0.0;

    //kernals
    public int dilateSize = 1;
    public int erodeSize = 3;

    //line stuff

    double cannyThres1 = 0;
    double cannyThres2 = 0;
    public int houghThres = 0;

    //utilties
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    public birdsEyeSampleSearchYellow(Telemetry telemetry){
        this.telemetry = telemetry;

//        telemetry.addLine("t open");
    }
    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
//        telemetry.addLine("t opened");

        frameSize = frame.size();
        yellowBasedFrame = frame.clone();
        groundSubmat = yellowBasedFrame.submat(new Rect(groundTopLeft, groundBottomRight));
        avg = (Core.mean(groundSubmat).val[0]+Core.mean(groundSubmat).val[1]+Core.mean(groundSubmat).val[2])/3;
        for(int x = 0;x<frameSize.width;x++){
            for(int y = 0;y<frameSize.height;y++){
                double[] colorAtPoint = yellowBasedFrame.get(y,x);

                double[] hsv = this.HSVcalc(colorAtPoint);
                double result;
                if(isBlue) {
                    result = (hsv[0] > 220 && hsv[0] < 250 && hsv[1] > 30 && hsv[2] > 20 && hsv[2] < 70) ? 255 : 0;
                }else{
                    result = ((hsv[0] > 350 || hsv[0] < 10) && hsv[1] > 60 && hsv[2] > 30) ? 255 : 0;

                }
                //                double result = 255;
//                result -=Math.abs(colorAtPoint[0]-avg-200)/2;
//                result -=Math.abs(colorAtPoint[1]-avg-150)/2;
//                result -= colorAtPoint[2]/3;
                result = Math.max(0,Math.min(result,255));
                //case red = punish for low g value
                //case metal = punish for hjgh blue value
                colorAtPoint[0]=colorAtPoint[1]=colorAtPoint[2]=result;
                yellowBasedFrame.put(y,x,colorAtPoint);
            }
        }
        Imgproc.cvtColor(yellowBasedFrame, yellowBasedFrame, Imgproc.COLOR_BGR2GRAY);

        if(seeYellowFrame)
            yellowBasedFrame.copyTo(frame);
        Mat postThreshold = new Mat();
        Imgproc.threshold(yellowBasedFrame, postThreshold, thresholdOffset+avg, 255, Imgproc.THRESH_BINARY);
        if(seePostThreshold)
            postThreshold.copyTo(frame);
        //morpho stuff (temp here)

        Mat dilateKernel = getStructuringElement(MORPH_RECT,
                new Size(2 * dilateSize + 1, 2 * dilateSize + 5),
                new Point(dilateSize, dilateSize));

        Mat erodeKernel = getStructuringElement(MORPH_ELLIPSE,
                new Size(2 * erodeSize + 1, 2 * erodeSize + 5),
                new Point(erodeSize, erodeSize));

        Mat morphTransform = new Mat();
        erode(postThreshold, morphTransform, erodeKernel);
        dilate(morphTransform, morphTransform, dilateKernel);
        if(seeMorphology)
            morphTransform.copyTo(frame);

        Mat cannyOut = new Mat();

        Canny(postThreshold, cannyOut, cannyThres1, cannyThres2);

        if(seeCanny)
            Imgproc.cvtColor(cannyOut, frame, Imgproc.COLOR_GRAY2BGR);

//        cannyOut.copyTo(frame);
        Mat houghLines = new Mat();
        Mat houghOutput = new Mat();

        Imgproc.threshold(yellowBasedFrame, houghOutput, 0, 255, Imgproc.THRESH_BINARY);

        HoughLinesP(cannyOut, houghLines, 1, Math.PI/180, houghThres);
        ArrayList<double[]> lineList = new ArrayList<>();
        for (int x = 0; x < houghLines.rows(); x++) {
            double[] l = houghLines.get(x, 0);
            lineList.add(l);
            Imgproc.line(houghOutput, new Point(l[0], l[1]), new Point(l[2], l[3]), BLUE, 2, Imgproc.LINE_AA, 0);
        }

        if(seeHough){
            houghOutput.copyTo(frame);
        }








        //findContours

        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        Imgproc.findContours(morphTransform, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

//        approxPolyDP();
        if(!seeMorphology && !seeHough && !seeCanny && !seePostThreshold) {
            for (MatOfPoint x : contours) {
                /// Source variable

                /// New variable
                MatOfPoint2f  newX = new MatOfPoint2f( x.toArray() );

                RotatedRect boundRect = Imgproc.minAreaRect(newX);
                Point points[] = new Point[4];
                boundRect.points(points);
                if(Imgproc.contourArea(x)>1500) {
                    for (int i = 0; i < 4; ++i) {
                        Imgproc.line(frame,
                                points[i],
                                points[(i + 1) % 4],
                                BLUE, 2);
                    }
                }
            }
        }


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
//    public ArrayList<double[]> getLines(){
//        return lineList;
//    }
    public static double[] HSVcalc(double[] rgb){
        double[] dout = new double[3];
        double r = rgb[0]/255;
        double g = rgb[1]/255;
        double b = rgb[2]/255;
        double max = Math.max(r,Math.max(g,b));
        double min = Math.min(r,Math.min(g,b));
        double delta = max-min;
        if(delta == 0){
            dout[0]=0;
        }
        else if(r==max){
            dout[0] = (60 * ((g - b) / delta) + 360) % 360;
        }else if(g==max){
            dout[0] = (60 * ((b - r) / delta) + 120) % 360;
        }else if(b==max){
            dout[0] = (60 * ((r - g) / delta) + 240) % 360;
        }
        dout[1]=max==0?0:(delta/max)*100;
        dout[2]=max*100;

        return dout;
    }
}
