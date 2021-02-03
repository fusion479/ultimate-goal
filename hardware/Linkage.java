package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Linkage {
    private Servo linkageServo;
    private boolean inAir = false;
    public void init(HardwareMap hwMap){
        linkageServo = hwMap.servo.get("linkageServo");

    }

    public void startPos(){
        linkageServo.setPosition(0.57);
    }

    public void endPos(){
        linkageServo.setPosition(0.37);
    }

    public void addPos(){
        linkageServo.setPosition(linkageServo.getPosition()+0.1);
    }
    public void subtractPos(){
        linkageServo.setPosition((linkageServo.getPosition()-0.1));
    }
    public double position(){
        return linkageServo.getPosition();
    }

    public void toggle(){
        if(!inAir){
            endPos();
        }
        else{
            startPos();
        }
        inAir = !inAir;
    }

    public boolean inAir(){
        return inAir;
    }
}
