package org.firstinspires.ftc.teamcode.opMode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DelayCommand;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Bumper;
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
    private FlywheelPIDF flywheel = new FlywheelPIDF();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private DelayCommand delay = new DelayCommand();
    private WobbleGoalV2 wobbleMech = new WobbleGoalV2();
    private int startWobbleTrajec = 0;
    private WebCamera camera = new WebCamera();
    private Bumper bumper = new Bumper();
    int speed = 1360;
    public void runOpMode() throws InterruptedException {
        Runnable toggleShooting = new Runnable() {
            @Override
            public void run() {
                linkage.toggle();
                flywheel.toggle(speed);
            }
        };
        Runnable toggleLinkage = new Runnable() {
            @Override
            public void run(){
                linkage.toggle();
            }
        };
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        bumper.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        wobbleMech.init(hardwareMap);
        camera.init(hardwareMap);
        drive.setFlywheel(flywheel);
        Pose2d startPose = new Pose2d(0, 12, Math.toRadians(0));
        drive.setPoseEstimate(startPose);
        int mode = 0;
        Trajectory pathInit = drive.trajectoryBuilder(startPose).
                addTemporalMarker(0, () -> {
                    flywheel.toggle(speed);
                    linkage.toggle();
                })
                .splineTo(new Vector2d(40, 20), 0)
                .splineToConstantHeading(new Vector2d(57, 3), 0).
                        addDisplacementMarker(() -> {
                            flywheelServo.burst(3);
                            camera.setPipeline();
                            telemetry.addData("Flywheel Velocity", flywheel.veloc());
                            telemetry.update();
                        })
                .build();

        Trajectory fourRings = drive.trajectoryBuilder(pathInit.end())
                .lineToLinearHeading(new Pose2d(110, 17, Math.toRadians(135))).
                        addTemporalMarker(1, () -> {
                            if (wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(() -> {
                            wobbleMech.toggleClamp();
                        })
                .build();

        //TRAJETORIES FOR FOURRING NEW FOR MTI oaisjdoij asiojd ioasjdio jasiojdio
        Trajectory reset = drive.trajectoryBuilder(fourRings.end())
                .addDisplacementMarker(()->{
                    wobbleMech.raise();
                })
                .lineToLinearHeading(new Pose2d(14,20,Math.toRadians(0)))
                .build();

        Trajectory getWobble = drive.trajectoryBuilder(reset.end())
                .addDisplacementMarker(()->{
                    wobbleMech.lower();
                })
                .strafeRight(20)
                .addDisplacementMarker(()->{
                    wobbleMech.clamp();
                    intake.intake(1);
                    flywheel.toggle(speed + 30);
                })
                .build();

        Trajectory getRings = drive.trajectoryBuilder(getWobble.end())
                .addDisplacementMarker(1, () -> {
                    delay.delay(toggleLinkage,1000);
                })
                .forward(26,
                        SampleMecanumDrive.getVelocityConstraint(7, DriveConstants.MAX_ANG_VEL,
                                DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive
                                .getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(()->{
                    flywheelServo.burst(3);
                })
                .build();
        
        Trajectory shootRings = drive.trajectoryBuilder(getRings.end())
                .addDisplacementMarker(()->{
                    linkage.toggle();
                })
                .lineToLinearHeading(new Pose2d(57,1.5))
                .addDisplacementMarker(()->{
                    intake.intake(0);
                })
                .build();

        Trajectory getLast = drive.trajectoryBuilder(getRings.end())
                .addDisplacementMarker(()->{
                    intake.intake(1);
                })
                .forward(6)
                .addDisplacementMarker(()->{
                    delay.delay(toggleLinkage, 450);
                })
                .build();
        Trajectory shootLast = drive.trajectoryBuilder(getLast.end())
                .lineToLinearHeading(new Pose2d(56,3))
                .addDisplacementMarker(()->{
                    flywheelServo.burst(2);
                    intake.intake(0);
                })
                .build();

        //___________________________________________________________________________________________
        Trajectory zeroRing = drive.trajectoryBuilder(pathInit.end())
                .lineToLinearHeading(new Pose2d(70,10,Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                    wobbleMech.lower();
                })
                .build();
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
        Trajectory afterWobble1OneRing = drive.trajectoryBuilder(oneRing.end())
                .addTemporalMarker(1,()->{
                intake.intake(1.0);

                })
                .lineToLinearHeading(new Pose2d(48, -3, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    wobbleMech.unClamp();
                    wobbleMech.raise();
                })
                .build();

        Trajectory getRingsOne = drive.trajectoryBuilder(afterWobble1OneRing.end()).
                addDisplacementMarker(() -> {
                    flywheel.toggle(speed);
                    linkage.toggle();
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

        Trajectory rePositionFour = drive.trajectoryBuilder(fourRings.end())
                .lineToSplineHeading(new Pose2d(60,0,Math.toRadians(180)))
                .build();
        Trajectory afterWobble1 = drive.trajectoryBuilder(rePositionFour.end()).
                addTemporalMarker(0,()->{
                    intake.intake(1.0);
                })
                .lineToLinearHeading(new Pose2d(45, 0, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    intake.intake(0);
                    wobbleMech.unClamp();
                })
                .build();

        Trajectory getRingsV4 = drive.trajectoryBuilder(afterWobble1.end()).
                addDisplacementMarker(() -> {
                    intake.intake(1.0);
                    wobbleMech.raise();
                }).
                forward(15).
                addDisplacementMarker(() -> {
                    flywheel.toggle(speed);
                    linkage.toggle();
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

        Trajectory shootPostRingRetrieval = drive.trajectoryBuilder(afterWobble1.end()).
                addTemporalMarker(0.5, () -> {
                    intake.outake(1);

                }).
                lineToSplineHeading(new Pose2d(56, 0, Math.toRadians(0))).
                addDisplacementMarker(() -> {
                    intake.intake(0);
                    flywheelServo.burst(3);
                }).
                build();
        Trajectory wobble2Retrieval = drive.trajectoryBuilder(shootPostRingRetrieval.end())
                .addDisplacementMarker(()->{
                    bumper.raise();
                })
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

        Trajectory wobble2RetrievalZero = drive.trajectoryBuilder(zeroRing.end())
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

        Trajectory wobble2RetrievalTurnOne = drive.trajectoryBuilder(wobble2Retrieval.end())
                .lineToSplineHeading(new Pose2d(15,4.0,Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    wobbleMech.lower();
                })
                .build();

        Trajectory oneRingsPrep = drive.trajectoryBuilder(wobble2RetrievalTurn.end())
                .lineTo(new Vector2d(93,-30))
                .build();

        Trajectory oneRingDupe = drive.trajectoryBuilder(oneRingsPrep.end()).
                lineToLinearHeading(new Pose2d(98,-18,Math.toRadians(165))).
                addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {
                            wobbleMech.toggleArm();
                        }
                    };
                    delay.delay(run,400);
                }).
                build();

        Trajectory endAutonOne = drive.trajectoryBuilder(oneRingDupe.end()).
                lineTo(new Vector2d(72, 0)).
                build();
        Trajectory fourRingsDupeInit = drive.trajectoryBuilder(shootLast.end()).
                lineTo(new Vector2d(103,0)).
                build();
        Trajectory fourRingsDupe = drive.trajectoryBuilder(fourRingsDupeInit.end())
                .lineToLinearHeading(new Pose2d(120, 10, Math.toRadians(180))).
                        addTemporalMarker(1, () -> {
                            if (wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(() -> {
                            wobbleMech.unClamp();
                            wobbleMech.raise();
                        })
                .build();

        Trajectory zeroRingDupe = drive.trajectoryBuilder(wobble2RetrievalTurn.end())
                .lineToLinearHeading(new Pose2d(75,10,Math.toRadians(180)))
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
        Trajectory endAuton = drive.trajectoryBuilder(fourRingsDupe.end()).
                lineTo(new Vector2d(72, 0)).
                build();
        telemetry.addData("Status", "Initialized");
        waitForStart();


        if (isStopRequested()) return;
        bumper.lower();
        drive.followTrajectory(pathInit);
        mode = camera.ringCount();
        telemetry.addData("Count",mode);
        telemetry.addData("Count2",camera.ringCount());
        telemetry.update();
        sleep(800);
        flywheel.toggle(0);
        linkage.toggle();
        if(mode == 4) {
            drive.followTrajectory(fourRings);
            drive.followTrajectory(reset);
            drive.followTrajectory(getWobble);
            drive.followTrajectory(getRings);
            flywheelServo.burst(3);
            sleep(800);
            linkage.toggle();
            drive.followTrajectory(getLast);
            drive.followTrajectory(shootLast);
            sleep(800);
            flywheel.toggle(0);
            linkage.toggle();
            drive.followTrajectory(fourRingsDupeInit);
            drive.followTrajectory(fourRingsDupe);
            drive.followTrajectory(endAuton);

            /*
            drive.followTrajectory(rePositionFour);
            drive.followTrajectory(afterWobble1);
            //drive.followTrajectory(getRingsV4);
            sleep(500);
            drive.followTrajectory(shootPostRingRetrieval);
            sleep(800);
            linkage.toggle();
            flywheel.toggle(0);
            drive.followTrajectory(wobble2Retrieval);
            drive.followTrajectory(wobble2RetrievalTurn);
            sleep(800);
            drive.followTrajectory(fourRingsDupeInit);
            drive.followTrajectory(fourRingsDupe);
            drive.followTrajectory(endAuton);

             */
        }
        else if (mode == 1){
            drive.followTrajectory(oneRing);
            sleep(500);
            drive.followTrajectory(afterWobble1OneRing);
            drive.followTrajectory(getRingsOne);
            drive.followTrajectory(shootPostRingRetrieval);
            sleep(2800);
            linkage.toggle();
            flywheel.toggle(0);
            intake.intake(0);
            drive.followTrajectory(wobble2Retrieval);
            drive.followTrajectory(wobble2RetrievalTurn);
            sleep(2000);
            drive.followTrajectory(oneRingsPrep);
            drive.followTrajectory(oneRingDupe);
            drive.followTrajectory(endAutonOne);
        }
        else{
            drive.followTrajectory(zeroRing);
            sleep(2000);
            wobbleMech.raise();
            drive.followTrajectory(wobble2RetrievalZero);
            drive.followTrajectory(wobble2RetrievalTurn);
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