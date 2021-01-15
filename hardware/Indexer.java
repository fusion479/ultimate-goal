package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
/*This code was originally written for prototyping the backroller. The backroller is now a part of the indexer and funtions as both.

* */
public class Indexer extends Mechanism {
    private DcMotor indexerM;

    public void init(HardwareMap hwMap) {
        indexerM = hwMap.dcMotor.get("indexer");
        indexerM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        indexerM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        indexerM.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void intake(float power){
        indexerM.setDirection(DcMotorSimple.Direction.REVERSE);
        indexerM.setPower(power);
    }

    public void outake(float power){
        indexerM.setDirection(DcMotorSimple.Direction.FORWARD);
        indexerM.setPower(power);
    }
}
