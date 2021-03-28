package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelWEncoders extends Mechanism{
    private DcMotorEx left;
    private DcMotorEx right;
    private boolean running = false;
    public double speed = 2795.52 * 1.0;
    private double defaultSpeed = 1.0;
    //just initializing the motors for use in flywheel
    public void init(HardwareMap hwMap) {
        left = hwMap.get(DcMotorEx.class,"left");
        right = hwMap.get(DcMotorEx.class,"right");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);


    }
    public void runEqual(double input){
        left.setVelocity(input * speed);
        right.setVelocity(input * speed);
    }
    public void toggle(){
        if(!running){
            runEqual(1.0);
        }

        else{
            runEqual(0.0);
        }

        running = !running;
    }

    public boolean running(){
        return running;
    }
    public double veloc(){
        return left.getVelocity();
    }
}
