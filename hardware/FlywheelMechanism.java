package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelMechanism extends Mechanism{

    //FlyWheelMechanism comprises of the flywheel and its servo
    private Flywheel flywheel = new Flywheel();
    private FlywheelServo flywheelServo = new FlywheelServo();

    public void init(HardwareMap hwMap){
        flywheel.init(hwMap);
        flywheelServo.init(hwMap);
    }

    //This method was for sake of simplicity in switching between 3 speeds
    public void run(int speedState){
        //MAXIMUM POOOOWWWEEEEER
        if(speedState == 0){
            flywheel.runEqual(1.0);
        }

        //Medium Speed
        if(speedState == 1){
            flywheel.runEqual(0.8);
        }

        //Lowest Speed
        if(speedState == 2){
            flywheel.runEqual(0.6);
        }
    }

}
