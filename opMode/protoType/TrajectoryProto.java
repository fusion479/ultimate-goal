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
import org.firstinspires.ftc.teamcode.hardware.WobbleGoal;

@Autonomous(name="TrajectoryProto")
public class TrajectoryProto extends LinearOpMode {
    private Flywheel flywheel = new Flywheel();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private DelayCommand delay = new DelayCommand();
    private WobbleGoal wobbleMech = new WobbleGoal();
    private int startWobbleTrajec = 0;
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
        wobbleMech.init(hardwareMap);
        Pose2d startPose = new Pose2d(0, -12, Math.toRadians(0));
        drive.setPoseEstimate(startPose);
        Runnable unClamp = new Runnable() {
            @Override
            public void run() {
                wobbleMech.unClamp();
            }
        };
        Runnable clamp = new Runnable() {
            @Override
            public void run() {
                wobbleMech.clamp();
            }
        };
        int startFourRings;
        Trajectory pathInit = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(40,-20),0)
                .splineToConstantHeading(new Vector2d(54,-3),0).
                addDisplacementMarker(()->{
                             shoot(4);
                })
                .build();
        Trajectory fourRings = drive.trajectoryBuilder(pathInit.end())
                .lineToLinearHeading(new Pose2d(103, 17, Math.toRadians(45))).
                addTemporalMarker(1,()-> {
                    wobbleMech.toggleArm();
                })
                .build();
        telemetry.addData("Status", "Initialized");
        waitForStart();

        if (isStopRequested()) return;
        delay.delay(toggleShooting,1000);
        drive.followTrajectory(pathInit);
        sleep(startWobbleTrajec);
        drive.followTrajectory(fourRings);

        /*Runnable flick = new Runnable() {
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
        delay.delay(toggleShooting,timeOffset+normalGap*4);*/


        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;

    }

    private void shoot(int discs){
        Runnable flick = new Runnable() {
            @Override
            public void run() {
                flywheelServo.flick();
            }
        };
        int normalGap = 350;
        int offset = 100;
        delay.delay(flick,offset);
        for(int i = 1; i < discs; i++){
            delay.delay(flick,i*normalGap + offset);
        }
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
        delay.delay(toggleShooting,discs*normalGap+offset);
        startWobbleTrajec = discs*normalGap+offset;
    }


}
