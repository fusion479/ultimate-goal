package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Acquirer extends Mechanism {
    private DcMotor acquirer;

    public void init(HardwareMap hwMap) {
        acquirer = hwMap.dcMotor.get("acquirer");
        acquirer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        acquirer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void intake(float power){
        acquirer.setDirection(DcMotorSimple.Direction.REVERSE);
        acquirer.setPower(power);
    }

    public void outake(float power){
        acquirer.setDirection(DcMotorSimple.Direction.FORWARD);
        acquirer.setPower(power);
    }
}
