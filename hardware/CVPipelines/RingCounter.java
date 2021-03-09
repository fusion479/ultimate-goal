package org.firstinspires.ftc.teamcode.hardware.CVPipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RingCounter extends OpenCvPipeline {
    //this contains the percentage of the region that is within the target color
    private double regionValue;
    //the workingMatrix is the matrix we work with...
    private Mat workingMatrix = new Mat();

    //ROI stands for region of interest; this defines the area that we are targeting
    static final Rect ROI = new Rect(
            new Point(0, 0),
            new Point(320, 240));

    //idk some code guide said to do this
    public RingCounter(){

    }

    //accessor method for regionValue; if you're confused as to why I didn't just make regionValue a public variable, you might be new to Java or have a different style
    public double regionValue(){
        return regionValue;
    }

    //This code makes it so that we can identify our target colors and it's percentage within our ROI
    @Override
    public final Mat processFrame(Mat input){
        //cvtColor converts a feed from a certain color format to another color format; in this case, we are converting from RGB to HSV
        Imgproc.cvtColor(input, workingMatrix, Imgproc.COLOR_RGB2HSV);

        /*lowHSV and highHSV are our thresholds
        IMPORTANT NOTE: openCV defines HSV parameters as such (Hue, Saturation, Value) where Hue is Range 0-179,
        Saturation is in range 0-255 and Value is in range 0-255 (all INCLUSIVE).
        NORMALLY, HSV is defined like so: Hue in range 0-360, Saturation in range 0.0-1.0 and Value in range 0.0-1.0.
        */
        Scalar lowHSV = new Scalar(5, 100, 50);
        Scalar highHSV = new Scalar(25, 255, 255);
        //This basically sets the thresholds; you all know about min max etc etc
        Core.inRange(workingMatrix, lowHSV, highHSV, workingMatrix);
        //creates the submat that we want to work with
        Mat region = workingMatrix.submat(ROI);
        //this counts the number of white pixels and divides it by the area of our ROI to figure out the percentage.
        regionValue = Core.sumElems(region).val[0] / ROI.area()/255;
        //you need to release the channel that we worked with or smthn smhtn; in this case we have to release region for some reason
        region.release();
        //line color
        Scalar lines = new Scalar(25,255,255);
        //Create the rectangle so that when testing we can see the ROI that we are working with
        Imgproc.rectangle(workingMatrix,ROI,lines);
        return workingMatrix;
    }

}
