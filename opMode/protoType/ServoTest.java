package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name="ServoTest",group="TeleOp")
public class ServoTest extends LinearOpMode {
    private Servo flickServo;
    public void init(HardwareMap hwMap){
        flickServo = hwMap.servo.get("flickServo");
    }
    @Override
    public void runOpMode() throws InterruptedException{
        boolean formerA = false;
        boolean formerB = false;
        boolean formerX = false;
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        init(hardwareMap);
        while(opModeIsActive()){
            if(gamepad1.a){
                formerA = true;
            }

            if(formerA){
                if(!gamepad1.a) {
                    flickServo.setPosition(0.5);
                    formerA = false;
                }
            }

            if(gamepad1.x){
                formerX = true;
            }

            if(formerX){
                if(!gamepad1.x){
                    flickServo.setPosition(0.0);
                    formerX = false;
                }
            }

            if(gamepad1.b){
                formerB = true;
            }

            if(formerB){
                if(!gamepad1.b) {
                    flickServo.setPosition(1.0);
                    formerB = false;
                }
            }

        }
    }
}
