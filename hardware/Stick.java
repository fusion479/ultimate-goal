package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Stick extends Mechanism{
    private Servo arm;
    public static double upper = 0.56;
    public static double lower = 0.2;
    public boolean raised = true;
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
        arm = hwMap.servo.get("stick");
        arm.setPosition(upper);
        }
    public void raise(){
        raised = true;
        arm.setPosition(upper);
    }
    public void lower(){
        raised = false;
        arm.setPosition(lower);
    }
}
