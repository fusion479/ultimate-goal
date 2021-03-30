package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends Mechanism {
    private DcMotor roller1;

    public void init(HardwareMap hwMap) {
        roller1 = hwMap.dcMotor.get("intake");
        roller1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        roller1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        roller1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void runInward(double trigger) {
        roller1.setDirection(DcMotorSimple.Direction.FORWARD);
        roller1.setPower(trigger);
    }

    public void runOutward(double trigger) {
        roller1.setDirection(DcMotorSimple.Direction.REVERSE);
        roller1.setPower(trigger);
    }

    public void setRollerSpeed(double speed) {
        roller1.setPower(speed);
    }
}
