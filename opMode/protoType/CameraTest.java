package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Camera;

@TeleOp(name = "CameraTest",group="TeleOp")
public class CameraTest extends LinearOpMode {
    private Camera camera;
    @Override
    public void runOpMode() throws InterruptedException {
        camera.init(hardwareMap);
        while(!opModeIsActive()&&!isStopRequested()){
            telemetry.addData("Status","Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){

        }
    }
}
