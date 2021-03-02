package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.WebCamera;

@TeleOp(name="WebcamTest",group="TeleOp")
public class WebcamTest extends LinearOpMode {
    private WebCamera camera = new WebCamera();

    @Override
    public void runOpMode() throws InterruptedException{
        camera.init(hardwareMap);

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            camera.setPipeline();
            telemetry.addData("Percent",camera.regionValue());
            telemetry.update();
        }
    }
}
