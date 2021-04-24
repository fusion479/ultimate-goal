package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Bumper extends Mechanism{
    private Servo arm;
    public static double upper = 0.11;
    public static double lower = 0.56;
    public boolean raised = false;
    public void toggle(){
        if(raised){
            lower();
            raised = false;
        }
        else{

            raise();
            raised = true;
        }
    }
    public void init(HardwareMap hwMap){
        arm = hwMap.servo.get("bumper");
        raise();    }

    public void raise(){
        raised = true;
        arm.setPosition(upper);
    }

    public void lower(){
        raised = false;
        arm.setPosition(lower);
    }
}
