package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class Prototype extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();
    @Override
    public void runOpMode() throws InterruptedException{
        boolean mode = true;
        flywheel.init(hardwareMap);
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        while(opModeIsActive()){
            telemetry.addData("Status", "Currently Looping");
            telemetry.addData("Status", mode);
            telemetry.addData("LeftTrigger",gamepad1.left_trigger);
            telemetry.addData("RightTrigger",gamepad1.right_trigger);


            telemetry.update();
            if(gamepad1.a){
                mode = !mode;
            }
            if(mode){
                flywheel.runEqual(gamepad1.left_trigger);
            }
            else {
                flywheel.runReverse(gamepad1.left_trigger);
            }

        }
    }

}
