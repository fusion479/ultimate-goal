package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleGoal extends Mechanism{
    private Servo clampServo;
    private Servo armServo;

    public void init(HardwareMap hwMap){
        clampServo = hwMap.servo.get("clampServo");
        armServo = hwMap.servo.get("armServo");
    }

    public void clamp(){
        clampServo.setPosition(1.0);
    }
    public void unClamp(){
        clampServo.setPosition(0.0);
    }

    public void raise(){
        armServo.setPosition(1.0);
    }

    public void lower(){
        armServo.setPosition(0.0);
    }
}
