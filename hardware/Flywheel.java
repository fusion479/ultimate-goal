package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
//This code may be subject to change following up to the finalization of our robot design.
//If that turns out to be the case, old code will be commented out.

public class Flywheel extends Mechanism {
    private DcMotor left;
    private DcMotor right;
    private double leftSpeed;
    private double rightSpeed;
    private boolean running = false;
    public double speed = 1.0;
    //just initializing the motors for use in flywheel
    public void init(HardwareMap hwMap) {
        left = hwMap.dcMotor.get("left");
        right = hwMap.dcMotor.get("right");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    //Sets the same amount of power through the flywheels in the same direction
    public void runEqual(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    //THE FOLLOWING COMMENTED OUT CODE IS NOT CURRENTLY USEFUL
    /*
    //Mainly used in early stages of flywheel prototyping. The motors run in opposite directions.
    //This code is a bit wack because if the direction is wrong it needs to be adjusted directly here.
    public void runReverse(double power) {
        left.setPower(power);
        right.setPower(power);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //Sends different amounts of power through the flywheel
    public void runSeparate(double leftPower, double rightPower) {
        left.setPower(leftPower);
        right.setPower(rightPower);

    }
    */


    //Reverses the direction of the flywheel. This is incompatible with runReverse method.
    public void reverseDirection(boolean forwards) {
        if (forwards) {
            left.setDirection(DcMotorSimple.Direction.FORWARD);
            right.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            left.setDirection(DcMotorSimple.Direction.REVERSE);
            right.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public boolean goingForwards(){
        return left.getDirection().equals(DcMotorSimple.Direction.FORWARD);
    }

    public void toggle(){
        if(!running){
            runEqual(speed);
        }

        else{
            runEqual(0.0);
        }

        running = !running;
    }

    public boolean running(){
        return running;
    }



}
