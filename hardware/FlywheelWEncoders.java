package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class FlywheelWEncoders extends Mechanism{
    private DcMotorEx left;
    private DcMotorEx right;
    private boolean running = false;
    public double speed = 1100;
    public static double power = 0.5;

    public static  double p1 = 1.7808;
    public static double p2 = 1.7953;
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

        right.setVelocityPIDFCoefficients(p2,p2*0.1,0,p2*10);
        left.setVelocityPIDFCoefficients(p1,p1*0.1,0,p1*10);


    }
    /*
    public void runEqual(double input){
        left.setVelocity(input * speed);
        right.setVelocity(input * speed);
    }
     */
    public void runEqual(double input){
        left.setPower(input);
        right.setPower(input);
    }
    public void toggle(){
        if(!running){
            runEqual(power);
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
