package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp
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

    // Our velocity controller
    private final VelocityPIDFController veloController = new VelocityPIDFController(MOTOR_VELO_PID, kV, kA, kStatic);

    @Override
    //CONVERT THIS TO AN OBJECT!
    public void runOpMode() throws InterruptedException {
        // SETUP MOTORS //
        // Change my id
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "right");

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

        // Insert whatever other initialization stuff you do here

        waitForStart();

        if (isStopRequested()) return;

        // Start the veloTimer
        veloTimer.reset();

        while (!isStopRequested()) {
            ///// Run the velocity controller ////

            // Target velocity in ticks per second
            double targetVelo = 0.0;

            // Call necessary controller methods
            veloController.setTargetVelocity(targetVelo);
            veloController.setTargetAcceleration((targetVelo - lastTargetVelo) / veloTimer.seconds());
            veloTimer.reset();

            lastTargetVelo = targetVelo;

            // Get the velocity from the motor with the encoder
            double motorPos = myMotor1.getCurrentPosition();
            double motorVelo = myMotor1.getVelocity();

            // Update the controller and set the power for each motor
            double power = veloController.update(motorPos, motorVelo);
            left.setPower(power);
            right.setPower(power);

            // Do your opmode stuff
        }
        public void runEqual(double power) {
            left.setPower(power);
            right.setPower(power);
        }
        public void toggle(){
            if(!running){
                runEqual(1.0);
            }

            else{
                runEqual(0.0);
            }

            running = !running;
        }
        public boolean running(){
            return running;
        }
        public double veloc(){
            return left.getVelocity();
        }
    }