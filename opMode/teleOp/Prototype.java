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
        flywheel.init(hardwareMap);
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            telemetry.addData("Status", "Currently Looping");
            telemetry.addData("Status", mode);
            telemetry.addData("LeftTrigger", gamepad1.left_trigger);
            telemetry.addData("RightTrigger", gamepad1.right_trigger);
            telemetry.addData("Direction Forwards?", forwards);
            telemetry.addData("The speedState is ",speedState);


            telemetry.update();
            if (gamepad1.a) {
                if(speedState < 5){
                    speedState ++;
                }
                else{
                    speedState = 0;
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

            if(mode) {

                if (speedState == 0) {
                    flywheel.runEqual(0.85);
                }
                else if(speedState == 1){
                    flywheel.runReverse(0.6);
                }

                else if(speedState == 2){
                    flywheel.runEqual(0.65);
                }
                else if(speedState == 3){
                    flywheel.runReverse( 0.65);
                }

                else if(speedState == 4){
                    flywheel.runEqual(gamepad1.left_trigger);
                }

                else if(speedState == 5){
                    flywheel.runReverse(gamepad1.left_trigger);
                }

            }
            else{
                flywheel.runEqual(0);
            }

            if (gamepad1.b) {
                forwards = !forwards;
                flywheel.reverseDirection(forwards);
            }


        }
    }

}
