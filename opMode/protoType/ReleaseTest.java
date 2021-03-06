package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Release;

@TeleOp
public class ReleaseTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Release release = new Release();
        release.init(hardwareMap);
        boolean formerA = false;
        boolean formerL = false;
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){
            if(gamepad1.a){
                formerA = true;
            }
            if(formerA){
                if(!gamepad1.a){
                    formerA = false;
                    release.release();
                }
            }
            if(gamepad1.left_bumper){
                formerL = true;
            }
            if(formerL){
                if(!gamepad1.left_bumper){
                    formerL = false;
                    release.retract();
                }
            }
            if(gamepad1.b){
                release.releaseServo.setPosition(1.0);
            }
            if(gamepad1.x)release.releaseServo.setPosition(0.0);
            if(gamepad1.y)release.releaseServo.setPosition(0.5);
        }
    }
}
