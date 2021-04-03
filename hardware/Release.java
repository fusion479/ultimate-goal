package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Release extends Mechanism{
    private Servo releaseServo;
    public static double releaseNum = 1.0;
    public void init(HardwareMap hwmap){
        releaseServo = hwmap.servo.get("release");
    }

    public void release(){
        releaseServo.setPosition(releaseNum);
    }


}
