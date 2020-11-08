package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Flywheel{
    private DcMotor left;
    private DcMotor right;
    private double leftSpeed;
    private double rightSpeed;

    public void init(HardwareMap hwMap){
        left = hwMap.dcMotor.get("left");
        right = hwMap.dcMotor.get("right");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.FORWARD);


    }

    public void runEqual(double trigger){
        left.setPower(trigger);
        right.setPower(trigger);
    }
    public void runReverse(double trigger){
        left.setPower(trigger);
        right.setPower(trigger);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void runSeparate(double triggerLeft, double triggerRight){
        left.setPower(triggerLeft);
        right.setPower(triggerRight);

    }

    public void setSpeed(double speed){
        left.setPower(speed);
        right.setPower(speed);
    }
}
