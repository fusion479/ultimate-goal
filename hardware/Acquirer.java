package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

//Code for acquirer mech
public class Acquirer extends Mechanism {
    private DcMotor acquirerM;

    //Goal in init is to just initialize the motors
    public void init(HardwareMap hwMap) {
        acquirerM = hwMap.dcMotor.get("acquirerM");
        acquirerM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        acquirerM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        acquirerM.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    //Simple intake and outake code, directing the motors to power and rotate in a certain direction.
    public void intake(float power){
        acquirerM.setDirection(DcMotorSimple.Direction.REVERSE);
        acquirerM.setPower(power);
    }

    public void outake(float power){
        acquirerM.setDirection(DcMotorSimple.Direction.FORWARD);
        acquirerM.setPower(power);
    }
}
