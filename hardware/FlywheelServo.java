package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FlywheelServo extends Mechanism{
    private Servo flickServo;

    public void init(HardwareMap hwMap){
        flickServo = hwMap.servo.get("flickServo");
        startPos();
    }

    public void startPos(){
        flickServo.setDirection(Servo.Direction.REVERSE);
        flickServo.setPosition(0.5);
    }

    public void endPos(){
        flickServo.setDirection(Servo.Direction.FORWARD);
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
        if(flickServo.getPosition() == 0.5) {
            endPos();
        }
        startPos();
    }
}
