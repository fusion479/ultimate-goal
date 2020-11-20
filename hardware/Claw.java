package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends Mechanism {
    private Servo arm;
    private Servo grip;
    private boolean opened;
    private boolean folded;


    public Claw(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init(HardwareMap hwMap) {
        grip = hwMap.servo.get("grip");
        arm = hwMap.servo.get("arm");

    }

    public void unfold() {
        arm.setPosition(0.4);
        folded = false;
        opMode.sleep(400);

    }

    public void fold() {
        arm.setPosition(0.01);
        folded = true;
        opMode.sleep(400);
    }

    public void open() {
        grip.setPosition(0.5);
        opened = true;
        opMode.sleep(400);
    }

    public void close() {
        grip.setPosition(0.01);
        opened = false;
        opMode.sleep(400);
    }


    public boolean getOpened() {
        return opened;
    }

    public boolean getFolded() {
        return folded;
    }
}