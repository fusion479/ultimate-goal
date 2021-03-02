package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DelayCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.CompleteIntake;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.hardware.Linkage;

@Autonomous(name="TrajectoryProto")
public class TrajectoryProto extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private DelayCommand delay = new DelayCommand();
    public void runOpMode() throws InterruptedException{
        Runnable toggleShooting = new Runnable() {
            @Override
            public void run() {
                if(linkage.inAir()) flywheel.runEqual(0);
                else{
                    flywheel.runEqual(1);
                }
                linkage.toggle();
            }
        };
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(40,-20),0)
                .splineToConstantHeading(new Vector2d(54,-3),0)
                .build();

        telemetry.addData("Status", "Initialized");
        waitForStart();

        if (isStopRequested()) return;
        delay.delay(toggleShooting,1000);
        drive.followTrajectory(myTrajectory);
        Runnable flick = new Runnable() {
            @Override
            public void run() {
                flywheelServo.flick();
            }
        };
        int timeOffset = 100;
        int normalGap = 350;
        delay.delay(flick,timeOffset);
        delay.delay(flick,timeOffset+normalGap);
        delay.delay(flick,timeOffset+normalGap*2);
        delay.delay(flick,timeOffset+normalGap*3);


        delay.delay(toggleShooting,timeOffset+normalGap*4);




        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;

    }


}
