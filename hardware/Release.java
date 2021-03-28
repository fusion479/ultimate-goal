package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Release extends Mechanism{
    private Servo releaseServo;

    public void init(HardwareMap hwmap){
        releaseServo = hwmap.servo.get("release");
    }

    public void release(){
        releaseServo.setPosition(1.0);
    }
    public void hold(){
        releaseServo.setPosition(0.0);
    }


}
