package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends Mechanism {
    private DcMotor roller1;
    private DcMotor roller2;
    private double rollerSpeed;

    public void init(HardwareMap hwMap) {
        roller1 = hwMap.dcMotor.get("intake");
        roller1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        roller1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        roller1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void runInward(double trigger) {
        roller1.setPower(trigger);
    }

    public void runOutward(double trigger) {
        roller1.setPower((-1)*(trigger));
    }

    public void setRollerSpeed(double speed) {
        roller1.setPower(speed);
    }
}
