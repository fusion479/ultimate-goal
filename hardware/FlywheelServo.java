package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FlywheelServo extends Mechanism {
    private Servo flickServo;

    public void init(HardwareMap hwMap){
        flickServo = hwMap.servo.get("flickServo");
        startPos();
    }

    public void startPos(){
        flickServo.setPosition(0.0);
    }

    public void endPos(){
        flickServo.setPosition(1.0);
    }

    public void setPosition(double x){
        flickServo.setPosition(x);
    }

    public void setDirectionForwards(){
        flickServo.setDirection(Servo.Direction.FORWARD);
    }

    public void setDirectionReverse(){
        flickServo.setDirection(Servo.Direction.REVERSE);
    }

    public void flick(){
        startPos();
        if(flickServo.getPosition()==0.0){
            endPos();
        }
        if(flickServo.getPosition()==1.0){
            startPos();
        }

    }
    public void forwards(){
        flickServo.setDirection(Servo.Direction.FORWARD);
    }
    public void backwards(){
        flickServo.setDirection(Servo.Direction.REVERSE);
    }
    public double currentPos(){
        return flickServo.getPosition();
    }
}
