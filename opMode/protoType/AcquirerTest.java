package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.Acquirer2;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
//Code for testing the acquirer with the addition of the Drivetrain.
@TeleOp(name="AcquirerTest",group="TeleOp")
public class AcquirerTest extends LinearOpMode {
    private Acquirer acquirer = new Acquirer();
    private Acquirer2 backRoller = new Acquirer2();
    private Drivetrain drive = new Drivetrain(this);

;    @Override
    public void runOpMode() throws InterruptedException{
        acquirer.init(hardwareMap);
        backRoller.init(hardwareMap);
        drive.init(hardwareMap);
        boolean formerA = false;

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
//why doesnt my code work
        while(opModeIsActive()){
            if(gamepad1.a) {
                acquirer.intake(1);
                backRoller.outake(1);
            }

            else if(gamepad1.b){
                acquirer.intake(1);
                backRoller.intake(1);
            }

            else{
                acquirer.outake(0);
                backRoller.intake(0);
            }

            boolean formerX = false;
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            telemetry.addData("Reverse?",drive.getReverse());
            telemetry.update();
            drive.teleDrive(r,robotAngle,rightX);

            if(gamepad1.x){
                formerX = true;
            }

            if(formerX){
                if(!gamepad1.x) {
                    formerX = false;
                    drive.reverse();
                }
            }


        }
    }

}
