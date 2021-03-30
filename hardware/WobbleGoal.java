package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DelayCommand;

public class WobbleGoal extends Mechanism{
    private Servo clampServo;
    private Servo armServoMain;
    private Servo armServoSupp;
    public boolean armRaised = true;
    public boolean clamped = true;
    private double armServoMainZERO = 0.55;
    private double armServoMainONE = 0.95;
    private double armServoSuppZERO = 0.5;
    private double armServoSuppONE = 0.1;
    private DelayCommand delay = new DelayCommand();

    public void init(HardwareMap hwMap){
        armServoMain = hwMap.servo.get("armMain");
        armServoSupp = hwMap.servo.get("armSupp");
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
        armServoSupp.setPosition(armServoSuppZERO);
        armRaised = true;
    }

    public void lower(){
        armServoMain.setPosition(armServoMainONE);
        armServoSupp.setPosition(armServoSuppONE);
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
