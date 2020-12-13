package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;

@TeleOp(name="FlickNotTeleOp",group="TeleOp")
public class FlickNotTeleOp extends LinearOpMode {
    private FlywheelServo flywheelServo = new FlywheelServo();

    @Override
    public void runOpMode() throws InterruptedException{
        boolean formerA = false;

        flywheelServo.init(hardwareMap);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){
            if(gamepad1.a){
                formerA = true;
            }

            if(formerA){
                if(!gamepad1.a) {
                    flywheelServo.flick();
                    formerA = false;
                }
            }

        }
    }

}
