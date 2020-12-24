package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Camera;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@TeleOp(name = "CameraTest",group="TeleOp")
public class CameraTest extends LinearOpMode {
    private Camera camera = new Camera();

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
                    camera.flash();
                    formerA = false;
                }
            }
            telemetry.addData("Percent",camera.regionValue());
            telemetry.update();
        }
    }
}
