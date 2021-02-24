package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DelayCommand;

//This code is for directing the servo that pushes discs into the flywheel.
public class FlywheelServo extends Mechanism {
    private Servo flickServo;
    //Delay will be used in flick
    private DelayCommand delay = new DelayCommand();

    //Classic init method. The startPos() is to ensure servo is in correct starting position.
    public void init(HardwareMap hwMap){
        flickServo = hwMap.servo.get("flickServo");
        startPos();
    }

    //This is to be adjusted for the "real" starting position. 0.0 position and actual starting position may differ.
    public void startPos(){
        flickServo.setPosition(0.3);
    }

    //This is to be adjusted for the "real" ending.
    public void endPos(){
        flickServo.setPosition(0.6);
    }

    //Methods to explicitly change to the 0 and 1 states of the servos.
    public void zeroPos() {flickServo.setPosition(0.0);}
    public void onePos() {flickServo.setPosition(1.0);}

    //Accessor method for setting servo position manually, although shouldn't be used too much.
    public void setPosition(double x){
        flickServo.setPosition(x);
    }

    //The commented out code turns out to be useless. Servo direction is only relevant in the case of a continuous servo.
    /*
    public void setDirectionForwards(){
        flickServo.setDirection(Servo.Direction.FORWARD);
    }

    public void setDirectionReverse(){
        flickServo.setDirection(Servo.Direction.REVERSE);
    }
    */

    //The commented out code turns out to not work because of how servo positions work. Furthermore, this code inefficient.
    /*
    public void flick(){
        startPos();
        if(flickServo.getPosition()==0.0){
            endPos();
        }
        if(flickServo.getPosition()==1.0){
            startPos();
        }

    }
    */

    //Method to flick the servo in the desired range of motion
    public void flick(){
        //Creating runnable versions of the commands to be used in delay method
        Runnable start = new Runnable() {
            public void run() {
                startPos();
            }};
        Runnable end = new Runnable() {
            public void run() {
                endPos();
            }};

        //sends servo to desired position
        endPos();
        delay.delay(start,150);
    }

    //Accessor method for servo position
    public double currentPos(){
        return flickServo.getPosition();
    }
}
