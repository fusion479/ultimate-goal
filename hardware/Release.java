package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DelayCommand;

@Config
public class Release extends Mechanism{
    public Servo releaseServo;
    public static double releaseNum = 1.0;
    public void init(HardwareMap hwmap){
        releaseServo = hwmap.servo.get("release");
    }
    DelayCommand delay = new DelayCommand();
    public void release(){
        releaseServo.setPosition(0.0);
        Runnable temp = new Runnable() {
            @Override
            public void run() {
                releaseServo.setPosition(0.5);
            }
        };
        delay.delay(temp,2500);

    }

    public void retract(){
        releaseServo.setPosition(1.0);
        Runnable temp = new Runnable() {
            @Override
            public void run() {
                releaseServo.setPosition(0.5);
            }
        };
        delay.delay(temp,3600);
    }




}
