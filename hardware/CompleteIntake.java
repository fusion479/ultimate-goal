package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CompleteIntake extends  Mechanism{
    private DcMotor acquirerM;
    private DcMotor indexerM;

    public void init(HardwareMap hwMap){
        acquirerM = hwMap.dcMotor.get("acquirerM");
        acquirerM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        acquirerM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        indexerM = hwMap.dcMotor.get("indexer");
        indexerM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        indexerM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void intake(float power){
        acquirerM.setPower(-power);
        indexerM.setPower(power);
    }

    public void outake(float power){
        acquirerM.setPower(power);
        indexerM.setPower(-power);
    }

}
