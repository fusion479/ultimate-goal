package org.firstinspires.ftc.teamcode.opMode.protoType;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.DelayCommand;
import org.firstinspires.ftc.teamcode.hardware.Linkage;

//Probably should be named something else but too far in to change it. Code for prototyping the flywheel.
@TeleOp
public class FlywheelTest extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();


    @Override
    public void runOpMode() throws InterruptedException {
        DelayCommand delay = new DelayCommand();
        //These values are all important in setting direction and speed.
        boolean mode = false;
        boolean forwards = true;
        int speedState = 0;
        boolean formerX = false;
        boolean formerA = false;
        boolean formerB = false;
        boolean formerY = false;
        boolean formerRBump = false;
        boolean formerLBump = false;
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        linkage.init(hardwareMap);
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            //Information about different values
            telemetry.addData("Status", "Currently Looping");
            telemetry.addData("Flywheel running", mode);
            telemetry.addData("LeftTrigger", gamepad1.left_trigger);
            telemetry.addData("RightTrigger", gamepad1.right_trigger);
            telemetry.addData("Direction Forwards?", forwards);
            telemetry.addData("The speedState is ",speedState);
            telemetry.addData("Servo POS ", flywheelServo.currentPos());
            telemetry.addData("Linkage POS",linkage.position());
            telemetry.update();

            //Changes the speed state between 3 modes
            if(gamepad1.a){
                formerA = true;
            }

            if(formerA){
                if(!gamepad1.a) {
                    if(speedState < 2){
                        speedState ++;
                    }
                    else{
                        speedState = 0;
                    }
                    formerA = false;
                }
            }

            //Turns the flywheel on and off.
            if(gamepad1.x){
                formerX = true;
            }

            if(formerX){
                if(!gamepad1.x) {
                    mode = !mode;
                    formerX = false;
                }
            }

            //mode determines when the flywheel runs; speed state determines the speed of the flywheel
            if(mode) {

                if (speedState == 0) {
                    flywheel.runEqual(1.0);
                }

                else if(speedState == 1) {
                    flywheel.runEqual(0.85);
                }

                else if(speedState == 2){
                    flywheel.runEqual(gamepad1.left_trigger);
                }

            }
            else{
                flywheel.runEqual(0);
            }

            //b button to reverse direction of flywheel if the direction turned out wrong.
            if(gamepad1.b){
                formerB = true;
            }

            if(formerB){
                if(!gamepad1.b) {
                    forwards = !forwards;
                    flywheel.reverseDirection(forwards);
                    formerB = false;
                }
            }

            //Used to test the flywheelServo and how well it worked with the flywheel.
            if(gamepad1.y){
                formerY = true;
            }
            if(formerY){
                if(!gamepad1.y){
                    flywheelServo.flick();
                    formerY = false;
                }
            }

            if(gamepad1.right_bumper){
                formerRBump = true;
            }
            if(formerRBump){
                if(!gamepad1.right_bumper){
                    linkage.toggle();
                    formerRBump = false;
                }
            }

            if(gamepad1.left_bumper){
                formerLBump = true;
            }
            if(formerLBump){
                if(!gamepad1.left_bumper){
                    linkage.subtractPos();
                    formerLBump = false;
                }
            }

        }
    }

}
