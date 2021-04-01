package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.TuningController;
import org.firstinspires.ftc.teamcode.hardware.VelocityPIDFController;

@Config
@TeleOp
public class LinkedMotorTuner extends LinearOpMode {
    public static PIDCoefficients MOTOR_VELO_PID = new PIDCoefficients(0, 0, 0);

    public static double kV = 0.0015;
    public static double kA = 0.0009;
    public static double kStatic = 0.0005;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    private final ElapsedTime veloTimer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // Change my id
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "right");

        // Reverse as appropriate
         left.setDirection(DcMotorSimple.Direction.REVERSE);
         right.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        VelocityPIDFController veloController = new VelocityPIDFController(MOTOR_VELO_PID, kV, kA, kStatic);
        TuningController tuningController = new TuningController();

        double lastTargetVelo = 0.0;
        double lastKv = kV;
        double lastKa = kA;
        double lastKstatic = kStatic;

        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addLine("Ready!");
        telemetry.update();
        telemetry.clearAll();

        waitForStart();

        if (isStopRequested()) return;

        tuningController.start();
        veloTimer.reset();

        while (!isStopRequested() && opModeIsActive()) {
            double targetVelo = tuningController.update();

            veloController.setTargetVelocity(targetVelo);
            veloController.setTargetAcceleration((targetVelo - lastTargetVelo) / veloTimer.seconds());
            veloTimer.reset();

            lastTargetVelo = targetVelo;

            telemetry.addData("targetVelocity", targetVelo);

            double motorPos = left.getCurrentPosition();
            double motorVelo = left.getVelocity();

            double power = veloController.update(motorPos, motorVelo);
            left.setPower(power);
            right.setPower(power);

            if(lastKv != kV || lastKa != kA || lastKstatic != kStatic) {
                lastKv = kV;
                lastKa = kA;
                lastKstatic = kStatic;

                veloController = new VelocityPIDFController(MOTOR_VELO_PID, kV, kA, kStatic);
            }

            telemetry.addData("velocity", motorVelo);
            telemetry.addData("error", targetVelo - motorVelo);

            telemetry.addData("upperBound", TuningController.rpmToTicksPerSecond(TuningController.TESTING_MAX_SPEED * 1.15));
            telemetry.addData("lowerBound", 0);
            telemetry.update();
        }
    }
}