package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.Acquirer2;

@TeleOp(name="AcquirerTest",group="TeleOp")
public class AcquirerTest extends LinearOpMode {
    private Acquirer acquirer = new Acquirer();
    private Acquirer2 backRoller = new Acquirer2();

    @Override
    public void runOpMode() throws InterruptedException{
        acquirer.init(hardwareMap);
        backRoller.init(hardwareMap);
        boolean formerA = false;

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
//why doesnt my code work
        while(opModeIsActive()){
            backRoller.intake(gamepad1.left_trigger);
            acquirer.intake(gamepad1.right_trigger);

        }
    }

}
