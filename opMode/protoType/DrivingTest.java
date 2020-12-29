package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
//We had issues with getting the DT to drive straight, so this code is meant to troubleshoot such issues.
@TeleOp(name="DrivingTest",group="TeleOp")
public class DrivingTest extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;


    @Override
    public void runOpMode() throws InterruptedException{
        //Remember, parts initialization.
        //Here I'm directly calling the motors since this is only for troubleshooting. Generally you want to write this stuff as a mechanism.
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        //Starting off by resetting the encoders.
        resetEncoders();
        boolean formerB = false;


        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();

        }

        while(opModeIsActive()){
            //Puts power and gets the DT to go STRAIGHT either forwards OR backwards.
            if(gamepad1.a){
                frontLeft.setPower(1.0);
                frontRight.setPower(1.0);
                backLeft.setPower(1.0);
                backRight.setPower(1.0);
            }
            //Important information about the distance the motors traveled.
            telemetry.addData("FL: Encoder value", frontLeft.getCurrentPosition());
            telemetry.addData("FR: Encoder value", frontRight.getCurrentPosition());
            telemetry.addData("BL: Encoder value", backLeft.getCurrentPosition());
            telemetry.addData("BR: Encoder value", backRight.getCurrentPosition());
            telemetry.addData("Forwards?",frontLeft.getDirection() == DcMotorSimple.Direction.FORWARD);

            telemetry.update();

            if(gamepad1.b){
                formerB = true;
            }
            if(formerB){
                if(!gamepad1.b){
                    resetEncoders();
                    reverse();
                    formerB = false;
                }
            }




        }
    }
    //Reverses the direction of the motors. Probably would be better to use .equals() but if it works it works. Not too concerned RN.
    public void reverse(){
        if(frontLeft.getDirection() == DcMotorSimple.Direction.FORWARD){
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else{
            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    //Reset the encoders for troubleshooting and starting the next test round.
    public void resetEncoders(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}