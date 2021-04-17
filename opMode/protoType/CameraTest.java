package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.PhoneCamera;
import org.firstinspires.ftc.teamcode.hardware.WebCamera;

//Code to test the camera and it's ability to distinguish the color of the rings.
@TeleOp(name = "CameraTest",group="TeleOp")
public class CameraTest extends LinearOpMode {
    private WebCamera camera = new WebCamera();

    //private OpenCvCamera phoneCam;
    @Override
    public void runOpMode() throws InterruptedException {
        boolean formerA = false;
        camera.init(hardwareMap);
        while(!opModeIsActive()&&!isStopRequested()){
            telemetry.addData("Status","Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){
            if(gamepad1.a){
                formerA = true;
            }
            if(formerA){
                if(!gamepad1.a){
                    formerA = false;
                }
            }
            telemetry.addData("Percent",camera.regionValue());
            telemetry.update();
        }
    }
}
