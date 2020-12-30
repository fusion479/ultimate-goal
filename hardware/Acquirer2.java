package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
/*This code was written for prototyping the acquirer. The backRoller in the prototype stages was
not connected to the indexer.
* */
public class Acquirer2 extends Mechanism {
    private DcMotor acquirerM;

    public void init(HardwareMap hwMap) {
        acquirerM = hwMap.dcMotor.get("backRoller");
        acquirerM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        acquirerM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        acquirerM.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void intake(float power){
        acquirerM.setDirection(DcMotorSimple.Direction.REVERSE);
        acquirerM.setPower(power);
    }

    public void outake(float power){
        acquirerM.setDirection(DcMotorSimple.Direction.FORWARD);
        acquirerM.setPower(power);
    }
}
