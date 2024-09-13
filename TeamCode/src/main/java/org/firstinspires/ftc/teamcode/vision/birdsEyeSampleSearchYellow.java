package org.firstinspires.ftc.teamcode.vision;

import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.HoughLines;
import static org.opencv.imgproc.Imgproc.HoughLinesP;
import static org.opencv.imgproc.Imgproc.MORPH_CLOSE;
import static org.opencv.imgproc.Imgproc.MORPH_CROSS;
import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.MORPH_GRADIENT;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class birdsEyeSampleSearchYellow implements VisionProcessor {
    //OpenCV sim stuff
    public double cannyThres1 = 0;
    public double cannyThres2 = 0;
    public int houghThres = 50;

    public boolean seeYellowFrame = false;
    public boolean seePostThreshold = false;
    public boolean seeMorphology = false;
    public boolean seeCanny = false;
    public boolean seeHough = false;
    //matrices, in order of appearance
    Mat yellowBasedFrame = new Mat();
    Mat groundSubmat = new Mat();

    //definitions/boundaries of matrices
    public double thresholdOffset = 80;

    Size frameSize = new Size();

    public Point groundTopLeft = new Point(1,1);
    public Point groundBottomRight = new Point(40,40);

    //Matrix Data
    double avg = 0.0;

    //kernals
    public int dilateSize = 3;
    public int erodeSize = 1;

    //utilties
    private static final Scalar BLUE = new Scalar(0, 0, 255);


    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        frameSize = frame.size();
        yellowBasedFrame = frame.clone();
        groundSubmat = yellowBasedFrame.submat(new Rect(groundTopLeft, groundBottomRight));
        avg = (Core.mean(groundSubmat).val[0]+Core.mean(groundSubmat).val[1]+Core.mean(groundSubmat).val[2])/3;
        for(int x = 0;x<frameSize.width;x++){
            for(int y = 0;y<frameSize.height;y++){
                double[] colorAtPoint = yellowBasedFrame.get(y,x);
                double result = (255-Math.abs(colorAtPoint[0]-avg-200))/3+(255-Math.abs(colorAtPoint[1]-avg-150))/3+(255-colorAtPoint[0])/3;
                result -= (255-Math.abs(colorAtPoint[0]-colorAtPoint[2]))/2;
                result = Math.max(result, 0);
                colorAtPoint[0]=colorAtPoint[1]=colorAtPoint[2]=result;
                yellowBasedFrame.put(y,x,colorAtPoint);
            }
        }
        Imgproc.cvtColor(yellowBasedFrame, yellowBasedFrame, Imgproc.COLOR_BGR2GRAY);

        if(seeYellowFrame)
            yellowBasedFrame.copyTo(frame);
        Mat postThreshold = new Mat();
        Imgproc.threshold(yellowBasedFrame, postThreshold, thresholdOffset, 255, Imgproc.THRESH_BINARY);
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
//        dilate(morphTransform, morphTransform, dilateKernel);
        if(seeMorphology)
            morphTransform.copyTo(frame);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat cannyOut = new Mat();

        Canny(postThreshold, cannyOut, cannyThres1, cannyThres2);

        if(seeCanny)
            Imgproc.cvtColor(cannyOut, frame, Imgproc.COLOR_GRAY2BGR);

//        cannyOut.copyTo(frame);
        Mat houghOutput = new Mat();

        HoughLines(cannyOut, houghOutput, 1, Math.PI/180, houghThres);
        if(seeHough){
            for (int x = 0; x < houghOutput.rows(); x++) {
                double[] l = houghOutput.get(x, 0);
                Imgproc.line(frame, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);
            }
        }


        //findContours

        /*
        Imgproc.findContours(morphTransform, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        if(!seeMorphology) {
            for (Mat x : contours) {
                Rect boundRect = Imgproc.boundingRect(x);
                Imgproc.rectangle(frame, boundRect, BLUE, 2);
            }
        }
        */

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
