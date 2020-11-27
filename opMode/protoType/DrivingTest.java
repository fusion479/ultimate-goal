package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;

@TeleOp(name="DrivingTest",group="TeleOp")
public class DrivingTest extends LinearOpMode {
    private Drivetrain drive = new Drivetrain(this);
    private Acquirer acquirer= new Acquirer();
    @Override
    public void runOpMode() throws InterruptedException{
        drive.init(hardwareMap);
        acquirer.init(hardwareMap);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }

        while(opModeIsActive()){
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

            acquirer.intake(gamepad1.right_trigger);
            acquirer.outake(gamepad1.left_trigger);

        }
    }
}