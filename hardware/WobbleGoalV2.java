package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DelayCommand;
@Config
public class WobbleGoalV2 extends Mechanism{
    private Servo clampServo;
    private Servo armServoMain;
    public boolean armRaised = true;
    public boolean clamped = true;
    public static double armServoMainZERO = 0.4;
    public static double armServoMainONE = 0.0;
    private DelayCommand delay = new DelayCommand();

    public void init(HardwareMap hwMap){
        armServoMain = hwMap.servo.get("armMain");
        clampServo = hwMap.servo.get("clampServo");
        clampServo.setPosition(0.2);
        Runnable raiseRun = new Runnable() {
            @Override
            public void run() {
                raise();
            }
        };
        delay.delay(raiseRun,1000);
    }


    public void clamp(){
        clampServo.setPosition(0.3);
        clamped = true;
    }
    public void onetime(){
        clampServo.setPosition(0.0);
    }
    public void unClamp(){
        clampServo.setPosition(0.7);
        clamped = false;
    }

    public void toggleClamp(){
        if(clamped) unClamp();
        else{
            clamp();
        }
    }
    public void raise(){

        armServoMain.setPosition(armServoMainZERO);
        armRaised = true;
    }

    public void lower(){
        armServoMain.setPosition(armServoMainONE);
        armRaised = false;
    }
    public void toggleArm(){
        if(armRaised) lower();
        else{
            clamp();
            raise();
        }
    }
}
