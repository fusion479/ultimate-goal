package org.firstinspires.ftc.teamcode.hardware;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Mechanism;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


public class FlywheelPIDF extends Mechanism {
    // Copy your PID Coefficients here
    public static PIDCoefficients MOTOR_VELO_PID = new PIDCoefficients(0, 0, 0);

    // Copy your feedforward gains here
    public static double kV = 1 / TuningController.rpmToTicksPerSecond(TuningController.MOTOR_MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;

    // Timer for calculating desired acceleration
    // Necessary for kA to have an affect
    private final ElapsedTime veloTimer = new ElapsedTime();
    private double lastTargetVelo = 0.0;
    DcMotorEx left;
    DcMotorEx right;
    // Our velocity controller
    private final VelocityPIDFController veloController = new VelocityPIDFController(MOTOR_VELO_PID, kV, kA, kStatic);
    private boolean running = false;

    //CHANGE TARGETVELO
    private double targetVelo = TuningController.rpmToTicksPerSecond(3000.0);

    public void init (HardwareMap hwMap){
        left = hwMap.get(DcMotorEx.class, "left");
        right = hwMap.get(DcMotorEx.class, "right");

        // Reverse as appropriate
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        // Ensure that RUN_USING_ENCODER is not set
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Turns on bulk reading
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public void update(){
        // Call necessary controller methods
        veloController.setTargetVelocity(targetVelo);
        veloController.setTargetAcceleration((targetVelo - lastTargetVelo) / veloTimer.seconds());
        veloTimer.reset();

        lastTargetVelo = targetVelo;

        // Get the velocity from the motor with the encoder
        double motorPos = left.getCurrentPosition();
        double motorVelo = left.getVelocity();

        // Update the controller and set the power for each motor
        double power = veloController.update(motorPos, motorVelo);
        left.setPower(power);
        right.setPower(power);
    }
    public void reset(){
        veloTimer.reset();
    }
//    should be deprecated since update() fixes power
//    public void runEqual(double power) {
//        left.setPower(power);
//        right.setPower(power);
//    }
//    public void toggle(){
//        if(!running){
//            runEqual(1.0);
//        }
//
//        else{
//            runEqual(0.0);
//        }
//
//        running = !running;
//    }
    public void setVelo(double newVelo){
        targetVelo = newVelo;
    }
    public boolean running(){
        return running;
    }
    public double veloc(){
        return left.getVelocity();
    }
}