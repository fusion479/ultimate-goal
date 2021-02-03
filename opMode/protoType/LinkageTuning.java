package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Linkage;

@TeleOp
public class LinkageTuning extends LinearOpMode {
    private Linkage linkage = new Linkage();

    @Override
    public void runOpMode() throws InterruptedException{
        linkage.init(hardwareMap);

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            telemetry.addData("Position",linkage.position());
            telemetry.update();
            if(gamepad1.a){
                linkage.addPos();
            }

            if(gamepad1.b){
                linkage.subtractPos();
            }
        }
    }

}
