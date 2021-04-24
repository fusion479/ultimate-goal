package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Bumper;
import org.firstinspires.ftc.teamcode.hardware.WebCamera;
@Config
@TeleOp
public class BumperTest extends LinearOpMode {
    private Bumper bumper = new Bumper();
    @Override
    public void runOpMode() throws InterruptedException{
        bumper.init(hardwareMap);
        boolean formerX = false;
        boolean formerY = false;
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while (opModeIsActive()) {
            if(gamepad1.x){
                formerX = true;
            }
            if(formerX){
                if(!gamepad1.x){
                    formerX = false;
                    bumper.raise();
                }
            }

            if(gamepad1.y){
                formerY = true;
            }

            if(formerY){
                if(!gamepad1.y){
                    formerY = false;
                    bumper.lower();
                }
            }

        }
    }
}
