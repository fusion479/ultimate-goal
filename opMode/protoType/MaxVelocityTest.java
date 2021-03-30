package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Config
@TeleOp
public class MaxVelocityTest extends LinearOpMode {
    DcMotorEx motor;
    DcMotorEx motor2;
    double currentVelocity;
    double maxVelocity = 0.0;

    double currentVelocity2;
    double maxVelocity2 = 0.0;
    public static  double p1 = 1.7808;
    public static double p2 = 1.7953;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "left");
        motor2 = hardwareMap.get(DcMotorEx.class, "right");
        motor2.setVelocityPIDFCoefficients(p2,p2*0.1,0,p2*10);
        motor.setVelocityPIDFCoefficients(p1,p1*0.1,0,p1*10);


        waitForStart();
        while (opModeIsActive()) {
            boolean runWithEncodes = true;
            if(runWithEncodes){
                if(gamepad1.a){
                    motor2.setVelocityPIDFCoefficients(p2,p2*0.1,0,p2*10);
                    motor.setVelocityPIDFCoefficients(p1,p1*0.1,0,p1*10);
                }
                if (gamepad1.b){
                    motor.setVelocity(0);
                    motor2.setVelocity(0);
                }
                if(gamepad1.y){
                    motor.setVelocity(1800);
                    motor2.setVelocity(1800);
                }

            }
            else{
                motor.setPower(1.0);
                motor2.setPower(1.0);
            }
            currentVelocity = motor.getVelocity();
            currentVelocity2 = motor2.getVelocity();
            if (currentVelocity > maxVelocity) {
                maxVelocity = currentVelocity;
            }
            if (currentVelocity2 > maxVelocity2) {
                maxVelocity2 = currentVelocity2;
            }
            //1840
            //F = 17.808
            //P = 1.7808
            //I = 0.17808
            telemetry.addData("current velocityLEFT", currentVelocity);
            telemetry.addData("maximum velocityLEFT", maxVelocity);

            //1820
            //F = 17.954
            //P = 1.7953
            //I = 0.17953
            telemetry.addData("current velocityRIGHT", currentVelocity2);
            telemetry.addData("maximum velocityRIGHT", maxVelocity2);
            telemetry.update();

        }
    }
}

