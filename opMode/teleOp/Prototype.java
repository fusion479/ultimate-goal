package org.firstinspires.ftc.teamcode.opMode.teleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Flywheel;

@TeleOp
public class Prototype extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();

    @Override
    public void runOpMode() throws InterruptedException {
        boolean mode = false;
        boolean forwards = true;
        int speedState = 0;
        boolean formerX = false;
        boolean formerA = false;
        boolean formerB = false;
        flywheel.init(hardwareMap);
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
                    flywheel.runEqual(0.85);
                }

                else if(speedState == 1) {
                    flywheel.runEqual(0.65);
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

        }
    }

}
