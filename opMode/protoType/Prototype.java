package org.firstinspires.ftc.teamcode.opMode.protoType;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.opMode.DelayCommand;

@TeleOp
public class Prototype extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();
    private FlywheelServo flywheelServo = new FlywheelServo();


    @Override
    public void runOpMode() throws InterruptedException {
        DelayCommand delay = new DelayCommand();
        boolean mode = false;
        boolean forwards = true;
        int speedState = 0;
        boolean formerX = false;
        boolean formerA = false;
        boolean formerB = false;
        boolean formerY = false;
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        flywheelServo.backwards();
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            telemetry.addData("Status", "Currently Looping");
            telemetry.addData("Flywheel running", mode);
            telemetry.addData("LeftTrigger", gamepad1.left_trigger);
            telemetry.addData("RightTrigger", gamepad1.right_trigger);
            telemetry.addData("Direction Forwards?", forwards);
            telemetry.addData("The speedState is ",speedState);
            telemetry.addData("Servo POS ", flywheelServo.currentPos());

            telemetry.update();

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

//B button to reverse direction of flywheel
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
            if(gamepad1.y){
                formerY = true;
            }
            if(formerY){
                if(!gamepad1.y){
                    /*
                    if(flywheelServo.currentPos() == 1.0){
                        flywheelServo.startPos();
                    }

                    else{
                        flywheelServo.endPos();
                    }*/
                    flywheelServo.startPos();
                    Runnable start = new Runnable() {
                        public void run() {
                            flywheelServo.endPos();
                        }};
                    Runnable end = new Runnable() {
                        public void run() {
                            flywheelServo.startPos();
                        }};
                    delay.delay(end,100);
                    delay.delay(start,200);

                    formerY = false;
                }
            }

        }
    }

}
