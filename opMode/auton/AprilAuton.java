package org.firstinspires.ftc.teamcode.opMode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DelayCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.CompleteIntake;
import org.firstinspires.ftc.teamcode.hardware.FlywheelPIDF;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.hardware.FlywheelWEncoders;
import org.firstinspires.ftc.teamcode.hardware.Linkage;
import org.firstinspires.ftc.teamcode.hardware.Release;
import org.firstinspires.ftc.teamcode.hardware.WebCamera;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoal;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoalV2;

@Autonomous
public class AprilAuton extends LinearOpMode {
    private boolean backyard = false;
    private FlywheelWEncoders flywheel = new FlywheelWEncoders();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private DelayCommand delay = new DelayCommand();
    private WobbleGoalV2 wobbleMech = new WobbleGoalV2();
    private int startWobbleTrajec = 0;
    private WebCamera camera = new WebCamera();
    private Release release = new Release();
    public void runOpMode() throws InterruptedException {
        Runnable toggleShooting = new Runnable() {
            @Override
            public void run() {
                linkage.toggle();
                flywheel.toggle();
            }
        };
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        wobbleMech.init(hardwareMap);
        camera.init(hardwareMap);
        release.init(hardwareMap);
        Pose2d startPose = new Pose2d(0, -12, Math.toRadians(0));
        drive.setPoseEstimate(startPose);
        int mode = 0;
        Trajectory pathInit = drive.trajectoryBuilder(startPose).
                addTemporalMarker(0, () -> {
                    flywheel.toggle();
                    linkage.toggle();
                })
                .splineTo(new Vector2d(40, -20), 0)
                .splineToConstantHeading(new Vector2d(56, 3), 0).
                        addDisplacementMarker(() -> {
                            flywheelServo.burst(3);
                            camera.setPipeline();
                            telemetry.addData("Flywheel Velocity", flywheel.veloc());
                            telemetry.update();
                        })
                .build();

        Trajectory fourRings = drive.trajectoryBuilder(pathInit.end())
                .lineToLinearHeading(new Pose2d(108, 20, Math.toRadians(135))).
                        addTemporalMarker(1, () -> {
                            if (wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(() -> {
                            wobbleMech.toggleClamp();
                        })
                .build();
        Trajectory zeroRing = drive.trajectoryBuilder(pathInit.end()).
                strafeLeft(24).
                addDisplacementMarker(()->{
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            wobbleMech.toggleClamp();
                        }
                    };
                    wobbleMech.lower();
                    delay.delay(temp,600);
                }).
                build();
        Trajectory wobble2RetrievalDupe = drive.trajectoryBuilder(zeroRing.end())
                .addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                })
                .lineToSplineHeading(new Pose2d(28, 2, Math.toRadians(180-5)))
                .addDisplacementMarker(() -> {
                    wobbleMech.lower();
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            wobbleMech.clamp();
                        }
                    };
                    delay.delay(temp, 1000);

                })
                .build();

        Trajectory zeroRingDupe = drive.trajectoryBuilder(wobble2RetrievalDupe.end())
                .lineToLinearHeading(new Pose2d(72,8,Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                    wobbleMech.raise();
                })
                .build();
        Trajectory backup = drive.trajectoryBuilder(zeroRingDupe.end()).
                back(10).
                build();
        Trajectory endAutonZero = drive.trajectoryBuilder(backup.end())
                .lineTo(new Vector2d(72,-20))
                .build();
        Trajectory oneRing = drive.trajectoryBuilder(pathInit.end())
                .lineTo(new Vector2d(93,18)).
                        addTemporalMarker(0.3, () -> {
                            if (wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(() -> {
                            wobbleMech.toggleClamp();
                            wobbleMech.raise();
                        })
                .build();
        Trajectory afterWobble1OneRing = drive.trajectoryBuilder(oneRing.end()).
                lineToLinearHeading(new Pose2d(48, -5, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    intake.intake(0.8);
                    wobbleMech.unClamp();
                    wobbleMech.raise();
                })
                .build();

        Trajectory getRingsOne = drive.trajectoryBuilder(afterWobble1OneRing.end()).
                addDisplacementMarker(() -> {
                    intake.intake(1);
                    wobbleMech.raise();
                }).
                forward(26).
                addDisplacementMarker(() -> {
                    Runnable temp2 = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    delay.delay(temp, 2300);
                }).
                build();


        Trajectory afterWobble1 = drive.trajectoryBuilder(fourRings.end()).
                lineToLinearHeading(new Pose2d(48, 0, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    intake.intake(0.8);
                    wobbleMech.unClamp();
                })
                .build();

        Trajectory getRingsV4 = drive.trajectoryBuilder(afterWobble1.end()).
                addDisplacementMarker(() -> {
                    intake.intake(1);
                    wobbleMech.raise();
                }).
                forward(15).
                addDisplacementMarker(() -> {
                    Runnable temp2 = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    delay.delay(temp, 2300);
                }).
                build();

        Trajectory shootPostRingRetrieval = drive.trajectoryBuilder(getRingsV4.end()).
                addTemporalMarker(0.5, () -> {
                    intake.outake(1);
                    flywheel.toggle();
                    linkage.toggle();
                }).
                lineToSplineHeading(new Pose2d(56, 0, Math.toRadians(0))).
                addDisplacementMarker(() -> {
                    intake.intake(0);
                    flywheelServo.burst(3);
                }).
                build();
        Trajectory wobble2Retrieval = drive.trajectoryBuilder(shootPostRingRetrieval.end())
                .lineToConstantHeading(new Vector2d(13.5, -10))

                .addDisplacementMarker(() -> {
                    wobbleMech.unClamp();
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            wobbleMech.clamp();
                        }
                    };
                    delay.delay(temp, 3000);

                })
                .build();
        Trajectory wobble2RetrievalTurn = drive.trajectoryBuilder(wobble2Retrieval.end())
                .lineToSplineHeading(new Pose2d(13.5,3.0,Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    wobbleMech.lower();
                })
                .build();
        Trajectory wobble2RetrievalTurnOne = drive.trajectoryBuilder(wobble2Retrieval.end())
                .lineToSplineHeading(new Pose2d(15,4.0,Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    wobbleMech.lower();
                })
                .build();

        Trajectory oneRingDupe = drive.trajectoryBuilder(wobble2RetrievalTurn.end()).
                lineToLinearHeading(new Pose2d(93,-24,Math.toRadians(165))).
                addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                    wobbleMech.toggleArm();
                }).
                build();

        Trajectory endAutonOne = drive.trajectoryBuilder(oneRingDupe.end()).
                lineTo(new Vector2d(72, 0)).
                build();
        Trajectory fourRingsDupeInit = drive.trajectoryBuilder(wobble2RetrievalTurn.end()).
                lineTo(new Vector2d(103,0)).
                build();
        Trajectory fourRingsDupe = drive.trajectoryBuilder(fourRingsDupeInit.end())
                .lineToLinearHeading(new Pose2d(120, 10, Math.toRadians(180))).
                        addTemporalMarker(1, () -> {
                            if (wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(() -> {
                            wobbleMech.unClamp();
                        })
                .build();
        Trajectory endAuton = drive.trajectoryBuilder(fourRingsDupe.end()).
                lineTo(new Vector2d(72, 0)).
                build();
        telemetry.addData("Status", "Initialized");
        waitForStart();


        if (isStopRequested()) return;
        release.release();
        drive.followTrajectory(pathInit);
        mode = camera.ringCount();
        telemetry.addData("Count",mode);
        telemetry.addData("Count2",camera.ringCount());
        telemetry.update();
        sleep(1500);
        flywheel.toggle();
        linkage.toggle();
        if(mode == 4) {
            drive.followTrajectory(fourRings);

            drive.followTrajectory(afterWobble1);
            drive.followTrajectory(getRingsV4);
            sleep(1000);
            drive.followTrajectory(shootPostRingRetrieval);
            sleep(2800);
            linkage.toggle();
            flywheel.toggle();
            drive.followTrajectory(wobble2Retrieval);
            drive.followTrajectory(wobble2RetrievalTurn);
            sleep(2000);
            drive.followTrajectory(fourRingsDupeInit);
            drive.followTrajectory(fourRingsDupe);
            drive.followTrajectory(endAuton);
        }
        else if (mode == 1){
            drive.followTrajectory(oneRing);
            sleep(500);
            drive.followTrajectory(afterWobble1OneRing);
            drive.followTrajectory(getRingsOne);
            drive.followTrajectory(shootPostRingRetrieval);
            sleep(2800);
            linkage.toggle();
            flywheel.toggle();
            intake.intake(0);
            drive.followTrajectory(wobble2Retrieval);
            drive.followTrajectory(wobble2RetrievalTurn);
            sleep(2000);
            drive.followTrajectory(oneRingDupe);
            drive.followTrajectory(endAutonOne);
        }
        else{
            drive.followTrajectory(zeroRing);
            sleep(2000);
            wobbleMech.raise();
            drive.followTrajectory(wobble2RetrievalDupe);
            sleep(2000);
            drive.followTrajectory(zeroRingDupe);
            drive.followTrajectory(backup);
            drive.followTrajectory(endAutonZero);
        }


        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;

    }
}